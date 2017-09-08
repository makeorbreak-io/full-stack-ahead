defmodule WhereToGoWeb.PageController do
  use WhereToGoWeb, :controller

  def index(conn, _params) do
    text conn, "whats up doc"
  end
end
