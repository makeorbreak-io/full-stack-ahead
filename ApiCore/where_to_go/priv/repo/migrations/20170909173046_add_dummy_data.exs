defmodule WhereToGo.Repo.Migrations.AddDummyData do
  use Ecto.Migration

  def change do
    # USERS
    #execute "insert into users (id, username, email, token, inserted_at, updated_at) values (, now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (1, 'user1', 'user1@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (2, 'user2', 'user2@gmail.com', 'user1tokne', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (3, 'user3', 'user3@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (4, 'user4', 'user4@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (5, 'user5', 'user5@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (6, 'user6', 'user6@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (7, 'user7', 'user7@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (8, 'user8', 'user8@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (9, 'user9', 'user9@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (10, 'user10', 'user10@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (11, 'user11', 'user11@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (12, 'user12', 'user12@gmail.com', 'user1token', now(), now())"

    # RATING
    #execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (, now(), now())"

    ## USER1
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 1, 1, now(), now())"

    ## USER2
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (2, 2, 2, now(), now())"

    ## USER3
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 3, 3, now(), now())"

    ## USER4
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 4, 4, now(), now())"

    ## USER5
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (1, 5, 5, now(), now())"

    ## USER6
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 6, 6, now(), now())"

    ## USER7
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (2, 7, 7, now(), now())"

    ## USER8
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 8, 8, now(), now())"

    ## USER9
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 9, 9, now(), now())"

    ## USER10
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 10, 10, now(), now())"

    ## USER11
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (1, 11, 11, now(), now())"

    ## USER12
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 12, 12, now(), now())"
  end
end
