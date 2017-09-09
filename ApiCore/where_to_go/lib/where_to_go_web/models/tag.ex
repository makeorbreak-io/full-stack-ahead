defmodule Tag do
  use WhereToGoWeb.Web, :model

  schema "tags" do
    field :name, :string
    has_many :poi_tags, PoiTag
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:name])
    |> validate_required([:name])
  end
end
