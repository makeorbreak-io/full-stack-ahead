defmodule WhereToGoWeb.RestaurantsClient do
    require IEx

    def get_restaurants do
        headers = get_authenticated_request_headers()

        get_cities_to_update()
        |> Enum.flat_map &get_restaurants_by_city(headers, &1)
        |> Enum.uniq_by  fn (c) -> c["name"] end
    end

    defp get_restaurants_by_city(headers, city) do
        response = request_businesses(headers, city)
        
        total = response["total"]
        businesses = Enum.map(response["businesses"], &build_restaurant_from_business/1)

        businesses ++ get_restaurants_by_city_recur(headers, city, total, length(businesses))
    end

    defp get_restaurants_by_city_recur(headers, city, total, offset \\ 0) do
        if  offset >= total do 
            []
        else
            response = request_businesses(headers, city, offset)

            businesses = Enum.map(response["businesses"], &build_restaurant_from_business/1)

            businesses ++ get_restaurants_by_city_recur(headers, city, total, offset + length(businesses))
        end
    end

    defp request_businesses(headers, city, offset \\ 0) do
        request_url = "https://api.yelp.com/v3/businesses/search?limit=50&categories=restaurants&location=#{city}&offset=#{offset}"
        Poison.decode!(HTTPoison.get!(request_url, headers).body)
    end
    
    defp build_restaurant_from_business(business) do
        res = Map.take(business, ["name", "url", "image_url", "price", "rating", "id"])

        categories = Enum.map(business["categories"], fn(cat) -> cat["title"] end)

        rest_with_cats = Map.put_new(res, "categories", categories)
        Map.put_new(rest_with_cats, "city", business["location"]["city"])
    end

    defp get_cities_to_update do
        [
            "Porto"
        ]
    end

    defp get_authenticated_request_headers do
        body = URI.encode_query(%{
            "grant_type": "client_credentials ",
            "client_id": System.get_env("CLIENT_ID"),
            "client_secret": System.get_env("CLIENT_SECRET")
        })        
        headers = [{"Content-type", "application/x-www-form-urlencoded"}]

        response = HTTPoison.post!("https://api.yelp.com/oauth2/token", body, headers)

        access_token = Poison.decode!(response.body)["access_token"]
        ["authorization": "Bearer #{access_token}", "accept": "application/json; charset=utf-8"]
    end
  end