require IEx
import WhereToGoWeb.RestaurantsClient
alias WhereToGoWeb.RestaurantsClient
import PointOfInterest
import Ecto.Query
import WhereToGo.Repo
alias WhereToGo.Repo

defmodule WhereToGoWeb.RestaurantsController do
    use WhereToGoWeb, :controller
  
    def update_restaurants(conn, _params) do
        restaurants = get_restaurants()
        models = Enum.map(restaurants, &poi_to_model/1)
        text conn, "Great Success"
    end

    defp poi_to_model(r) do
        poi = %PointOfInterest{
            city: r["city"],
            name: r["name"],
            price: r["price"],
            rating_api: r["rating"],
            url: r["url"],
            image_url: r["image_url"]
        }       
        
        result = Repo.insert!(poi)
        IEx.pry
        categories = Enum.map(r["categories"], &tag_to_model(result.id, &1))
        Enum.each(categories, fn x -> Repo.insert!(x) end)
    end

    defp tag_to_model(id, t) do
        IEx.pry
        %Tag{
            point_of_interest_id: id,
            name: t 
        }
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