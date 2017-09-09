defmodule WhereToGoWeb.LoginController do
  use WhereToGoWeb, :controller
  import Ecto.Query
  alias WhereToGo.Repo

  def login(conn, %{"email" => email, "token" => token} = _params) do
    user_db = Repo.one(from user in User, where: user.email == ^email)
    
    new_user_changeset =
      case user_db do
        nil -> User.changeset(%User{}, %{email: email, token: token})
        _ ->  User.changeset(user_db, %{token: token})
      end

    status_code = 
      case Repo.insert_or_update(new_user_changeset) do
        {:ok, _changeset} -> 200
        {:error, _changeset} -> 400 
      end

    conn
    |> put_status(status_code)
    |> text("")
  end

end
