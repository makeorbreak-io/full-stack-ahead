#!flask/bin/python
from flask import Flask, request, jsonify
from controllers.CollaborativeFilteringController import CollaborativeFilteringController

from repositories.mariadb.RatingsRepositoryMariaDB import RatingsRepositoryMariaDB #ToDo: deleteme
from repositories.mariadb.PlacesRepositoryMariaDB import PlacesRepositoryMariaDB #ToDo: deleteme

app = Flask(__name__)
cfController = CollaborativeFilteringController()

@app.route('/')
def index():
    return "Hello :)"

@app.route('/train')
def train():
    result = cfController.train()
    return jsonify(trained = True)

@app.route('/predict', methods=['POST'])
def predict():
    userId = request.form['userId']
    #return "result for user %s" % cfController.predict(userId=int(userId))
    return jsonify(data = cfController.predict(userId=int(userId)))

@app.route('/test/<number>')
def test(number):
    return jsonify(
        number=number,
        test=True
    )

#ToDo: deleteme
@app.route('/ratings')
def test_ratings():
    repo = RatingRepositoryMariaDB(host="maria", port=3306, user="fsa", password="xourixos", db="where2go") #get from env
    result = repo.get_dataframe_ratings().values.tolist() #returning dataframe as a list
    return jsonify(
        test=True,
        ratings=result
    )

#ToDo: deleteme
@app.route('/places')
def test_places():
    repo = PlacesRepositoryMariaDB(host="maria", port=3306, user="fsa", password="xourixos", db="where2go") #get from env
    result = repo.get_dataframe_places().values.tolist() #returning dataframe as a list
    return jsonify(
        test=True,
        ratings=result
    )

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0') #threaded=True