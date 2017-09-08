defmodule WhereToGoWeb.RestaurantsClient do
    require IEx
    @base_url "https://api.yelp.com/v2/search?category_filter=restaurants&location="

    def get_joke do
        joke = HTTPoison.get "http://api.icndb.com/jokes/random"
        Poison.decode!(joke.body)["value"]["joke"]
    end

    def get_restaurants do
        restaurants = Enum.flat_map(get_cities_to_update(), get_restaurant)
        IEx.pry
        restaurants
    end

    defp get_restaurant(city) do
        headers = build_request_headers()
        request_url = @base_url <> city
        response = HTTPoison.get(request_url, headers)
        IEx.pry
        response.body
    end

    defp build_request_headers do
        ["authorization": "do me", "accept": "application/json; charset=utf-8"]
    end

    defp get_request_token do
        client = OAuth2.Client.new([
            strategy: OAuth2.Strategy.AuthCode,
            client_id: "3385HsgouTXxfFjgk1zfaQ",
            client_secret: "h2UQMb4zqWMiGRBslsEqCbFBpKiDvKLfLYay2YIxaMwZ75pHv008MiG9ReZ3n7uT",
            site: "https://api.yelp.com",
            redirect_uri: "/oauth2/token"
          ])


    end

    defp get_cities_to_update do
        [
            "Porto"
        ]
    end
  end