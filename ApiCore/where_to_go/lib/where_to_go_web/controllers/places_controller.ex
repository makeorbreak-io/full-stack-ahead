defmodule WhereToGoWeb.PlacesController do
    use WhereToGoWeb, :controller
  
    def index(conn, _params) do
      data = [%{isto: "ja", faz: "algo"}, %{de: "jeito"}]

      conn 
      |> send_resp(200, Poison.encode!(data, pretty: true))
    end
  end