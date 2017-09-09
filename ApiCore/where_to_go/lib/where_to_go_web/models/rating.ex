defmodule Rating do
  use WhereToGoWeb, :model

  schema "ratings" do
    field :rating, :integer
    belongs_to :users, User, foreign_key: :user_id
    belongs_to :points_of_interest, PointOfInterest, foreign_key: :point_of_interest_id
    timestamps()
  end

  def changeset(struct, params \\ %{}) do
    struct
    |> cast(params, [:rating, :user_id, :point_of_interest_id])
    |> validate_required([:rating, :user_id, :point_of_interest_id])
  end
end
