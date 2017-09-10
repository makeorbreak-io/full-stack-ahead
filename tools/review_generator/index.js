var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'xourixos',
  port     : 3307,
  database : 'where2go'
});

var MAX_REVIEW_PER_POI = 1000;
var TOTAL_USERS = 1000;

var do_some_magic = function(conn, user, userIndex, place){
    var counter = 0;
    var rating = place.rating_api;
    //console.log(user.id); console.log("..........");console.log(place); console.log(userIndex);
    if(userIndex <= TOTAL_USERS/3){
        rating -= 1;
    } else if(userIndex >= 2*TOTAL_USERS/3){
        rating += 1;
    }
    var placeId = place.id;
    conn.query("insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values ("+rating+", "+user.id+", "+placeId+", now(), now())");
};

connection.connect();

  connection.query('SELECT id, rating_api FROM point_of_interest', function (error, places, fields) {
    if (error) throw error;
    connection.query('SELECT id FROM users', function (error, users, fields) {
        if (error) throw error;
        places.forEach(function(place){
            users.forEach(function(user, ui){
                do_some_magic(connection, user, ui, place);
            });
        });
      });
  });

