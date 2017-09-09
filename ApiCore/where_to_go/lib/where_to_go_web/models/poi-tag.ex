defmodule PoiTag do
  use WhereToGoWeb, :model

  schema "poi_tags" do
    belongs_to :points_of_interest, PointOfInterest, foreign_key: :point_of_interest_id
    belongs_to :tags, Tag, foreign_key: :tag_id
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:point_of_interest_id, :tag_id])
    |> validate_required([:point_of_interest_id, :tag_id])
  end
end
