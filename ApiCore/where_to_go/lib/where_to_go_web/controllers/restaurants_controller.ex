require IEx
import WhereToGoWeb.RestaurantsClient
alias WhereToGoWeb.RestaurantsClient

defmodule WhereToGoWeb.RestaurantsController do
    use WhereToGoWeb, :controller
  
    def update_restaurants(conn, _params) do
        text conn, get_restaurants()
    end

    def predict(conn, _params) do                                        
        request_url = "http://ai:5000/predict"
        userid = conn.body_params["userId"]

        response = HTTPoison.post!(request_url, {:form, [userId: userid]}, %{"Content-type" => "application/x-www-form-urlencoded"}) #URI.encode_query(%{userId: userid}), headers)
        IEx.pry
        IO.inspect(response.body, label: "CONN: ")
        
        json conn, Poison.decode!(response.body)
    end

  end