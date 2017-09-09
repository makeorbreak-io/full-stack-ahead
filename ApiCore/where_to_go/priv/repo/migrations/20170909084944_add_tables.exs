defmodule WhereToGo.Repo.Migrations.AddTables do
  use Ecto.Migration

  def change do

    create table(:users) do
      add :username, :string
      add :email, :string
      add :token, :string
      add :provider, :string
      timestamps()
    end

    create table(:points_of_interest) do
      add :name, :string
      add :price, :string
      add :rating_api, :float
      add :url, :string
      add :image_url, :string
      timestamps()
    end

    create table(:ratings) do
      add :rating, :integer
      add :user_id, references(:users)
      add :point_of_interest_id, references(:points_of_interest)
      timestamps()
    end

    create table(:tags) do
      add :name, :string
      timestamps()
    end

    create table(:poi_tags) do
      add :point_of_interest_id, references(:points_of_interest)
      add :tag_id, references(:tags)
      timestamps()
    end

  end
end
