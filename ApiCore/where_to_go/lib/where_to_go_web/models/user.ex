defmodule User do
  use WhereToGoWeb.Web, :model

  schema "users" do
    field :username, :string
    field :email, :string
    field :token, :string
    field :provider, :string
    has_many :ratings, Rating
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:username, :email, :token, :provider])
    |> validate_required([:email, :token, :provider])
  end
end
