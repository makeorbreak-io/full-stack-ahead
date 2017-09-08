defmodule WhereToGoWeb.RestaurantsClient do
    require IEx
    @auth_url "https://api.yelp.com/oauth2/token"
    @base_url "https://api.yelp.com/v3/businesses/search?categories=restaurants&location="

    def get_joke do
        joke = HTTPoison.get "http://api.icndb.com/jokes/random"
        Poison.decode!(joke.body)["value"]["joke"]
    end

    def get_restaurants do
        access_token = get_access_token()
        restaurants = Enum.flat_map(get_cities_to_update(), &get_restaurants_by_city(access_token, &1))
        IEx.pry
        restaurants
    end

    defp get_restaurants_by_city(access_token, city) do        
        request_url = @base_url <> city
        headers = ["authorization": "Bearer #{access_token}", "accept": "application/json; charset=utf-8"]

        response = HTTPoison.get!(request_url, headers)

        Poison.decode!(response.body)
    end    

    defp get_cities_to_update do
        [
            "Porto",
            "Lisbon"
        ]
    end

    defp get_access_token do
        body = build_request_body()
        headers = [{"Content-type", "application/x-www-form-urlencoded"}]

        response = HTTPoison.post!(@auth_url, body, headers)

        Poison.decode!(response.body)["access_token"]
    end

    defp build_request_body do
        URI.encode_query(%{
            "grant_type": "client_credentials ",
            "client_id": System.get_env("CLIENT_ID"),
            "client_secret": System.get_env("CLIENT_SECRET")
        })
    end
  end