defmodule WhereToGoWeb.ErrorController do
  use WhereToGoWeb, :controller
  import Ecto.Query
  alias WhereToGo.Repo
  require Logger
  
  def handle_mismatch(conn, _params) do
    {peer_ip_list, peer_port} = conn.peer

    peer_ip = peer_ip_list |> Tuple.to_list |> Enum.join(".")
    remote_ip = conn.remote_ip |> Tuple.to_list |> Enum.join(".")

    request_info = "Peer ip: " <> Poison.encode!(peer_ip) <> " | Peer port: " <> Poison.encode!(peer_port) <> " | Remote Ip: " <> Poison.encode!(remote_ip)
  
    case File.open("bad_access.log", [:append]) do
      {:ok, file} ->
        IO.puts(file, request_info)
        File.close(file)
      _ ->
        Logger.error "Couldn't open bad_access.log ."
    end
  
    conn
    |> put_status(404)
    |> text ""
  end
end
