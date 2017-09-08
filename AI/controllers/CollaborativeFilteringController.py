

class CollaborativeFilteringController():

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
        return ["res1", "res2", "res3"]
