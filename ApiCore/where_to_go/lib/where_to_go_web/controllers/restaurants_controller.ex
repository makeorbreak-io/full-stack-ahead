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
            name: r["name"],
            price: r["price"],
            rating_api: r["rating"],
            url: r["url"],
            image_url: r["image_url"]
        }       
        
        result = Repo.insert!(poi)

        categories = Enum.map(r["categories"], &tag_to_model(result.id, &1))        
        Enum.each(categories, fn x -> Repo.insert!(x) end)
    end

    defp tag_to_model(id, t) do 
        %Tag{
            point_of_interest_id: id,
            name: t 
        }
    end
  end