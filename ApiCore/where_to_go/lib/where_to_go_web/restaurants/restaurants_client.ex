defmodule WhereToGoWeb.RestaurantsClient do
    require IEx

    def get_restaurants do
        headers = get_authenticated_request_headers()
        restaurants = Enum.flat_map(get_cities_to_update(), &get_restaurants_by_city(headers, &1))

        File.write("../restaurants", Poison.encode!(restaurants), [:binary])
        IEx.pry
        restaurants
    end

    defp get_restaurants_by_city(headers, city) do
        #request and increment offset to get all the results (returned in groups of 50)
        offset = 0
        request_url = "https://api.yelp.com/v3/businesses/search?limit=50&offset=#{offset}&categories=restaurants&location=#{city}"

        response = HTTPoison.get!(request_url, headers)

        businesses = Poison.decode!(response.body)["businesses"]
        Enum.map(businesses, &build_restaurant_from_business/1)
    end
    
    defp build_restaurant_from_business(business) do
        rest = Map.take(business, ["name", "url", "image_url", "price", "rating"])

        categories = Enum.map(business["categories"], fn(cat) -> cat["title"] end)

        Map.put_new(rest, "categories", categories)
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