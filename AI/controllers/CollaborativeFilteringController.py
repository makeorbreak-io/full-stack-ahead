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
    eventsRepo = PlacesRepositoryMariaDB(host=HOST, port=PORT, user=USER, password=PASSWORD, db=DB_NAME)
    ratingsRepo = RatingsRepositoryMariaDB(host=HOST, port=PORT, user=USER, password=PASSWORD, db=DB_NAME)

    def __init__(self):
        self.__load_data__()
        self.train()

    def __load_data__(self):
        print "loading data"

    def train(self):
        print "training"
        return True
    
    def predict(self, userId):
        print "predicting"
        if self.rbm is not None:
            print "running"
            result = self.rbm.predict(places_df=self.places_df, userId=userId)
            return result
        return []