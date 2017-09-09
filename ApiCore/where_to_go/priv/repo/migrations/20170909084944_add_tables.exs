defmodule WhereToGo.Repo.Migrations.AddTables do
  use Ecto.Migration

  def change do

    create table(:users) do
      add :username, :string
      add :email, :string
      add :token, :text
      add :provider, :string
      timestamps()
    end
    
    create table(:point_of_interest) do
      add :name_id, :string
      add :city, :string
      add :name, :string
      add :price, :string
      add :rating_api, :float
      add :url, :string
      add :image_url, :string
      timestamps()
    end

    create table(:tags) do
      add :point_of_interest_id, references(:point_of_interest)
      add :name, :string
      timestamps()
    end

    create table(:ratings) do
      add :rating, :integer
      add :user_id, references(:users)
      add :point_of_interest_id, references(:point_of_interest)
      timestamps()
    end
  end
end
