defmodule WhereToGo.Repo.Migrations.AddDummyData do
  use Ecto.Migration

  def change do
    # USERS
    #execute "insert into users (id, username, email, token, inserted_at, updated_at) values (, now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (1, 'bruno@mail.com', 'bruno@mail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (2, 'goncalo@mail.com', 'goncalo@mail.com', 'user1tokne', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (3, 'tiago@gmail.com', 'tiago@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (4, 'ricardo@gmail.com', 'ricardo@gmail.com', 'user1token', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (5, 'user5', 'user5@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (6, 'user6', 'user6@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (7, 'user7', 'user7@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (8, 'user8', 'user8@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (9, 'user9', 'user9@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (10, 'user10', 'user10@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (11, 'user11', 'user11@gmail.com', 'ntoken', now(), now())"
    execute "insert into users (id, username, email, token, inserted_at, updated_at) values (12, 'user12', 'user12@gmail.com', 'ntoken', now(), now())"

    # RATING
    #execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (, now(), now())"

    ## USER5
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 5, 277, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3.5, 5, 206, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 5, 356, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 5, 192, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.6, 5, 488, now(), now())"

    ## USER6
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 6, 62, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 6, 100, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 6, 123, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 6, 363, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 6, 455, now(), now())"


    ## USER7
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 7, 21, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 7, 52, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 7, 70, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 7, 107, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 7, 125, now(), now())"

    ## USER8
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.3, 8, 125, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 8, 150, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 8, 165, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 8, 167, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 8, 182, now(), now())"

    ## USER9
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 9, 7, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 9, 9, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 9, 14, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 9, 15, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 9, 34, now(), now())"

    ## USER10
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 10, 20, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3.5, 10, 220, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3.5, 10, 274, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 10, 297, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 10, 412, now(), now())"

    ## USER11
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 11, 4, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 11, 11, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 11, 41, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 11, 46, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 11, 51, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (2.5, 11, 174, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4.5, 11, 176, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 11, 202, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 11, 203, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3.5, 11, 273, now(), now())"

    ## USER12
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 12, 221, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (5, 12, 280, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3, 12, 434, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (3.5, 12, 472, now(), now())"
    execute "insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values (4, 12, 511, now(), now())"
  end
end
