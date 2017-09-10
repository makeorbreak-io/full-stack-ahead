defmodule WhereToGoWeb.RatingsController do
  use WhereToGoWeb, :controller
  import Ecto.Query
  import WhereToGo.Repo
  alias WhereToGo.Repo

  def add_rating(conn, %{"place_id" => place_id, "rating" => rating} = params) do
    [auth_token] = get_req_header(conn, "authorization")

    user = Repo.one(from user in User, where: user.token == ^auth_token)
    case user do
      nil ->
        conn
        |> put_status(404)
        |> text("")
      _ ->

       place = Repo.one(from poi in PointOfInterest, where: poi.id == ^place_id)
       case place do
          nil ->
            conn
            |> put_status(404)
            |> text("")
          _ ->

            rating_db = Repo.one(
              from rating in Rating,
                where: rating.user_id == ^user.id,
                where: rating.point_of_interest_id == ^place.id
            )

            changeset_rating =
              case rating_db do
                nil -> Rating.changeset(%Rating{}, %{user_id: user.id, point_of_interest_id: place.id, rating: rating})
                _ ->  Rating.changeset(rating_db, %{rating: rating})
              end

            status_code = 
              case Repo.insert_or_update(changeset_rating) do
                {:ok, _changeset} -> 
                  HTTPoison.get!("http://ai:5000/train", %{}, stream_to: self)
                  200
                {:error, _changeset} -> 400 
              end

            conn
            |> put_status(status_code)
            |> text("")
       end
    end
  end

end