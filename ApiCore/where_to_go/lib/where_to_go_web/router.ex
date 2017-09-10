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
    get "/api/v1/categories", CategoriesController, :get_categories
    get "/api/v1/restaurants", RestaurantsController, :update_restaurants
    get "/api/v1/predict", RestaurantsController, :predict
    get "/api/v1/rating", RatingsController, :train
    post "/rating", RatingsController, :add_rating

    get "/*path", ErrorController, :handle_mismatch
  end

  # Other scopes may use custom stacks.
  # scope "/api", WhereToGoWeb do
  #   pipe_through :api
  # end
end
