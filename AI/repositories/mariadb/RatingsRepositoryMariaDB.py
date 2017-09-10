import pandas as pd
import MySQLdb

from repositories.IRatingsRepository import IRatingRepository

USER_ID_STR = 'UserID'
PLACE_ID_STR = 'PlaceID'
RATING_STR = 'Rating'

class RatingsRepositoryMariaDB(IRatingRepository):

    ratings_df = None
    host = None
    port = None
    user = None
    password = None
    db = None
    conn = None

    def __init__(self, host, port, user, password, db):
        self.host = host
        self.port = port
        self.user = user
        self.password = password
        self.db = db
    
    def __connect__(self):
        self.conn = MySQLdb.connect(host=self.host, port=self.port,user=self.user, passwd=self.password, db=self.db)
    
    def __disconnect__(self):
        self.conn.close()

    def get_dataframe_ratings(self):
        self.__connect__()
        self.ratings_df = pd.read_sql('select user_id, point_of_interest_id, rating from ratings;', con=self.conn)
        self.ratings_df.columns = [USER_ID_STR, PLACE_ID_STR, RATING_STR]
        print self.ratings_df.head()
        self.__disconnect__()
        return self.ratings_df