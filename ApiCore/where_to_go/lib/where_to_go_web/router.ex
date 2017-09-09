defmodule WhereToGoWeb.Router do
  use WhereToGoWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    #plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", WhereToGoWeb do
    pipe_through :browser # Use the default browser stack
    
    get "/", PageController, :index
    post "/login", LoginController, :login
  end

  scope "/api/v1/", WhereToGoWeb do
    pipe_through :browser # Use the default browser stack

    get "/", PageController, :index
    get "/categories", CategoriesController, :get_categories
    get "/restaurants", RestaurantsController, :update_restaurants
    post "/predict", RestaurantsController, :predict
    post "/rating", RatingsController, :add_rating
  end

  # Other scopes may use custom stacks.
  # scope "/api", WhereToGoWeb do
  #   pipe_through :api
  # end
end
