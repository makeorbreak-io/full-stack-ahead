#!flask/bin/python
from flask import Flask, request, jsonify
from controllers.CollaborativeFilteringController import CollaborativeFilteringController


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

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0') #threaded=True