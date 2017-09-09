import pandas as pd
import MySQLdb

from repositories.IPlacesRepository import IPlacesRepository

PLACE_ID_STR = 'PlaceID'
PLACE_INDEX_STR = 'Place Index'


class PlacesRepositoryMariaDB(IPlacesRepository):

    places_df = None
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

    def get_dataframe_places(self):
        self.__connect__()
        self.places_df = pd.read_sql('select id from point_of_interest;', con=self.conn)
        self.places_df.columns = [PLACE_ID_STR]
        self.places_df[PLACE_INDEX_STR] = self.places_df.index
        print self.places_df.head()
        self.__disconnect__()
        return self.places_df
