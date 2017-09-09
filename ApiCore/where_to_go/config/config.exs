# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# General application configuration
config :where_to_go,
  ecto_repos: [WhereToGo.Repo]

# Configures the endpoint
config :where_to_go, WhereToGoWeb.Endpoint,
  url: [host: "maria"],
  secret_key_base: "EraF5Zr4wDbb31iyIKVYZ8WpmkgVRRxQ4sVQNAxMJBwMS90+h2ihZiAoghGuyQXt",
  render_errors: [view: WhereToGoWeb.ErrorView, accepts: ~w(html json)],
  pubsub: [name: WhereToGo.PubSub,
           adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env}.exs"
