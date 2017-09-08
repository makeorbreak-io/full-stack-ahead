defmodule WhereToGoWeb.PageController do
  use WhereToGoWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
