defmodule WhereToGoWeb.PageController do
  use WhereToGoWeb, :controller
  require IEx

  def index(conn, _params) do
    IEx.pry  
    text conn, "whats up doc"
  end
end
