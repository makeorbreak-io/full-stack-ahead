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
        categories = Enum.map(r["categories"], &tag_to_model(result.id, &1))
        Enum.each(categories, fn x -> Repo.insert!(x) end)
    end

    defp tag_to_model(id, t) do
        %Tag{
            point_of_interest_id: id,
            name: t 
        }
    end

    def predict(conn, _params) do                                        
        request_url = "http://ai:5000/predict"
        [auth_token] = get_req_header(conn, "authorization")
        user = Repo.one(from user in User, where: user.token == ^auth_token)
        case user do
            nil -> 
                conn |> put_status 404
                |> text("login dude")
            _ ->
                body = {:form, [userId: user.id]}
                headers = %{"Content-type" => "application/x-www-form-urlencoded"}
                response = HTTPoison.post!(request_url, body, headers)
                decoded_response = Poison.decode!(response.body)
                
                if length(decoded_response["data"]) != 0 do
                    IO.puts "----------------PythonRecommendations-----------------"
                    ids = Enum.map(decoded_response["data"], fn(r) -> round(List.first(r)) end)
                    
                    restaurants = Repo.all(
                        from poi in PointOfInterest,
                        where: poi.id in ^ids,
                        preload: [:tags])

                    filtered_response = Enum.filter(restaurants, &filter_func(_params, &1))
            
                    predicted_ratings = Enum.map(decoded_response["data"], 
                    fn(r) ->
                        %{id: round(List.first(r)), predicted_rating: List.last(r)} 
                    end)
                    encoded_restaurants = Enum.map(Enum.take(filtered_response, 50), &encode_to_map(predicted_ratings, &1))
                    json conn, encoded_restaurants
                else
                    json conn, get_fallback_recommendations(_params)
                end
        end
    end

    defp get_fallback_recommendations(_params) do
        IO.puts "----------------FALLBACK-----------------"
        restaurants = Repo.all(
            from poi in PointOfInterest,
            order_by: [desc: poi.rating_api],
            preload: [:tags])

        filtered_response = Enum.filter(restaurants, &filter_func(_params, &1))

        Enum.map(Enum.take(filtered_response, 50), &encode_to_map/1)
    end

    defp encode_to_map(predicted_ratings, f_response) do
        pr = Enum.find(predicted_ratings, fn(pr) -> pr.id == f_response.id end)

        %{
            id: f_response.id,
            url: f_response.url,
            rating: f_response.rating_api,
            price: f_response.price,
            name: f_response.name,
            image_url: f_response.image_url,
            categories: Enum.map(f_response.tags, fn(t) -> t.name end),
            predicted_rating: pr.predicted_rating
        }
    end

    defp encode_to_map(f_response) do
        %{
            id:  f_response.id,
            url: f_response.url,
            rating: f_response.rating_api,
            price: f_response.price,
            name: f_response.name,
            image_url: f_response.image_url,
            categories: Enum.map(f_response.tags, fn(t) -> t.name end)
        }
    end

    defp filter_func(filters, r) do
        filter_city(filters, r) && filter_category(filters, r)
    end

    defp filter_city(filters, r) do
        has_city_filter = Map.has_key?(filters, "city")

        if has_city_filter do
            r.city == filters["city"]
        else
            true
        end
    end

    defp filter_category(filters, r) do
        has_category_filter = Map.has_key?(filters, "category")

        if has_category_filter do
            Enum.any?(r.tags, fn(t) -> t.name == filters["category"] end) 
        else
            true
        end
    end
  end

  