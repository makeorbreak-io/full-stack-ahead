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

  /*connection.query('SELECT id, rating_api FROM point_of_interest', function (error, places, fields) {
    if (error) throw error;
    connection.query('SELECT id FROM users', function (error, users, fields) {
        if (error) throw error;
        places.forEach(function(place){
            users.forEach(function(user, ui){
                do_some_magic(connection, user, ui, place);
            });
        });
      });
  });*/

  connection.query('SELECT DISTINCT name FROM tags', function(err, tags){
      var offset = 0;
      var counter = 0;
      console.log(err);
      console.log(tags);
      var tmpUsersId = [];
      tags.forEach(function(tag){

        connection.query('SELECT id FROM users limit 100 offset '+offset, function(err, users){

            users.forEach(function(user){
                connection.query('SELECT * FROM point_of_interest LEFT JOIN tags ON point_of_interest.id = tags.point_of_interest_id WHERE tags.name LIKE "'+tag.name+'"',
                function(err, results){
                    results.forEach(function(result){
                        //console.log(result);
                        var randomVal = Math.floor(Math.random()*5)+1;
                        var userId = user.id;
                        var placeId = result.point_of_interest_id;
                        //console.log('insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values ('+randomVal+', '+userId+', '+placeId+', now(), now())')    
                        connection.query('insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values ('+randomVal+', '+userId+', '+placeId+', now(), now())')    
                    });
                 });
            });
        });
        offset += 100;
      });
      /*connection.query('SELECT id FROM users', function(err, users){
         // users.forEach(function(user){
          //    tmpUsersId
         // });
        //console.log(users);
        console.log("tags");console.log(tags)
        tags.forEach(function(tag) {
            for(var i=counter; i<counter+100; i++){
                //console.log(tag);
                var randomVal = 0;
                var userId = users[i].id;
                var placeId = tag.point_of_interest_id;
                console.log("Random "+randomVal+", userId "+userId+", placeId "+placeId+", tagId "+tag.id);
                console.log(tags.length);
                //connection.query('insert into ratings (rating, user_id, point_of_interest_id, inserted_at, updated_at) values ('+randomVal+', '+userId+', '+placeId+', now(), now())')
            }
            offset += 100;
        });
    });*/
    
  });

//  connection.end();
