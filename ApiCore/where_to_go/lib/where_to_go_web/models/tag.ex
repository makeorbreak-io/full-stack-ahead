defmodule Tag do
  use WhereToGoWeb, :model

  schema "tags" do
    belongs_to :point_of_interest, PointOfInterest, foreign_key: :point_of_interest_id
    field :name, :string
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:name])
    |> validate_required([:name])
  end
end
