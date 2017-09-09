from repositories.mariadb.RatingsRepositoryMariaDB import RatingsRepositoryMariaDB
from repositories.mariadb.PlacesRepositoryMariaDB import PlacesRepositoryMariaDB 
from models.RestrictedBoltzmannMachine import RestrictedBoltzmannMachine

HOST = "maria"
PORT = 3306
USER = "fsa"
PASSWORD = "xourixos"
DB_NAME = "where2go"

class CollaborativeFilteringController():

    places_df = None
    ratings_df = None
    places_ratings_df = None
    user_group_df = None
    rbm = None
    placesRepo = PlacesRepositoryMariaDB(host=HOST, port=PORT, user=USER, password=PASSWORD, db=DB_NAME)
    ratingsRepo = RatingsRepositoryMariaDB(host=HOST, port=PORT, user=USER, password=PASSWORD, db=DB_NAME)

    def __init__(self):
        self.__load_data__()
        self.train()

    def __load_data__(self):
        print "loading data"
        self.places_df = self.placesRepo.get_dataframe_places()
        self.ratings_df = self.ratingsRepo.get_dataframe_ratings()
        print "Total of places registered %s" % len(self.places_df)
        self.places_ratings_df = self.places_df.merge(self.ratings_df, on='PlaceID') #merge by PlaceID
        self.places_ratings_df = self.places_ratings_df.drop('Timestamp', axis=1).drop('Title', axis=1)
        #ToDo: remove the above line, it's only good to check if the data is correct
        print self.places_ratings_df
        self.user_group_df = self.places_ratings_df.groupby('UserID')
        print self.user_group_df.first().head()

    def train(self, hidden_layers = 20):
        print "training"
        self.rbm = RestrictedBoltzmannMachine(hidden_layers = hidden_layers, visible_layers=len(self.places_df))
        self.rbm.train(users_group=self.user_group_df, places_df=self.places_df, learning_rate=1.0, epochs=15, batch_size=100, total_user_training=1000 )
        return True
    
    def predict(self, userId):
        print "predicting"
        if self.rbm is not None:
            print "running"
            result = self.rbm.predict(places_df=self.places_df, userId=userId)
            return result
        return []