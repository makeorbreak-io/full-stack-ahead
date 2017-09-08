require IEx
import WhereToGoWeb.RestaurantsClient
alias WhereToGoWeb.RestaurantsClient

defmodule WhereToGoWeb.RestaurantsController do
    use WhereToGoWeb, :controller
  
    def update_restaurants(conn, _params) do
        text conn, get_restaurants()
    end
  end