defmodule PointOfInterest do
  use WhereToGoWeb, :model

  schema "point_of_interest" do
    field :city, :string
    field :name, :string
    field :price, :string
    field :rating_api, :float
    field :url, :string
    field :image_url, :string
    has_many :ratings, Rating
    has_many :tags, Tag
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:name, :price, :rating_api, :url, :image_url, :city])
    |> validate_required([:name, :price, :rating_api, :url, :city])
  end
end
