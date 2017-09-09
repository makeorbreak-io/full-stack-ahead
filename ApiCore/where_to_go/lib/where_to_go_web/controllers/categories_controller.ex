defmodule WhereToGoWeb.CategoriesController do
  use WhereToGoWeb, :controller
  import Ecto.Query
	import WhereToGo.Repo
  alias WhereToGo.Repo

  def get_categories(conn, _params) do
      categories = Repo.all(from tag in Tag, distinct: true, select: tag.name)
			json conn, categories_db
  end

end
