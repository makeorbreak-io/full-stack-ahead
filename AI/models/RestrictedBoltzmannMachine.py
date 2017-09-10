import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt # to remove
import pandas as pd

class RestrictedBoltzmannMachine():
    """
    References
        http://www.machinelearning.org/proceedings/icml2007/papers/407.pdf
        https://cognitiveclass.ai/courses/deep-learning-tensorflow/
        http://www.chioka.in/differences-between-l1-and-l2-as-loss-function-and-regularization/
    """

    visibleBiases = None
    hiddenBiases = None
    weights = None
    totalHiddenLayers = 0
    totalVisibleLayers = 0

    session = None
    v0 = None
    previousWeights = None
    previousHiddenBiases = None
    previousVisibleBiases = None
    training_list = None
    position_userId = None

    def __init__(self, hidden_layers, visible_layers):
        self.totalVisibleLayers = visible_layers
        self.totalHiddenLayers = hidden_layers
        self.visibleBiases = tf.placeholder('float', [visible_layers])
        self.hiddenBiases = tf.placeholder('float', [hidden_layers])
        # each neuron in the hiddenBiases will end up learning a new feature
        self.weights = tf.placeholder('float', [visible_layers, hidden_layers])

    def __get_training_list__(self, totalUsersForTraining, users_group, places_df):
        training_list = []
        self.position_userId = []
        for userID, userData in users_group:
            tmpPlacesRating_list = [0] * len(places_df)
            lastUserId = None
            for num, place in userData.iterrows():
                tmpPlacesRating_list[place['Place Index']] = place['Rating']/5.0
            lastUserId = place['UserID']
            self.position_userId.append(lastUserId)
            training_list.append(tmpPlacesRating_list)
            if totalUsersForTraining == 0:
                break
            totalUsersForTraining = totalUsersForTraining - 1
        return training_list

    def __process_input__(self):
        v0 = tf.placeholder('float', [None, self.totalVisibleLayers])
        _h0 = tf.nn.sigmoid(tf.matmul(v0, self.weights) + self.hiddenBiases)
        h0 = tf.nn.relu(tf.sign(_h0 - tf.random_uniform(tf.shape(_h0))))
        return v0, _h0, h0

    def __reconstruction__(self, h0):
        _v1 = tf.nn.sigmoid(tf.matmul(h0, tf.transpose(self.weights)) + self.visibleBiases)
        v1 = tf.nn.relu(tf.sign(_v1 - tf.random_uniform(tf.shape(_v1))))
        h1 = tf.nn.sigmoid(tf.matmul(v1, self.weights) + self.hiddenBiases)
        return _v1, v1, h1

    def __set_error_function__(self, v0, v1):
        error = v0 - v1
        sumError = tf.reduce_mean(error * error) #Mean Absolute Error Function
        return sumError

    def __set_contrastive_divergence__(self, v0, h0, v1, h1, learning_rate): #learning_rate aka alpha
        weight_pos_gradient = tf.matmul(tf.transpose(v0), h0)
        weight_neg_gradient = tf.matmul(tf.transpose(v1), h1)
        contrastiveDivergence = (weight_pos_gradient - weight_neg_gradient) / tf.to_float(tf.shape(v0)[0])
        newWeights = self.weights + learning_rate * contrastiveDivergence
        newVisibleBiases = self.visibleBiases + learning_rate * tf.reduce_mean(v0 - v1, 0)
        newHiddenBiases = self.hiddenBiases + learning_rate * tf.reduce_mean(h0 - h1, 0)
        return newWeights, newVisibleBiases, newHiddenBiases

    def __init_tf_vars__(self):
        currentWeights = np.zeros([self.totalVisibleLayers, self.totalHiddenLayers], np.float32)
        currentVisibleBiases = np.zeros([self.totalVisibleLayers], np.float32)
        currentHiddenBiases = np.zeros([self.totalHiddenLayers], np.float32)
        previousWeights = np.zeros([self.totalVisibleLayers, self.totalHiddenLayers], np.float32)
        previousVisibleBiases = np.zeros([self.totalVisibleLayers], np.float32)
        previousHiddenBiases = np.zeros([self.totalHiddenLayers], np.float32)
        return currentWeights, currentVisibleBiases, currentHiddenBiases, previousWeights, previousVisibleBiases, previousHiddenBiases

    def __create_chart__(self, errors_list):
        fig = plt.figure()
        plt.plot(errors_list, figure=fig)
        plt.ylabel('Error')
        plt.xlabel('Epoch')
        fig.savefig('/app/train_Data.png')

    def train(self, users_group, places_df, epochs, batch_size, total_user_training, learning_rate=1.0):
        training_list = self.__get_training_list__(
                                        totalUsersForTraining=total_user_training,
                                        users_group=users_group,
                                        places_df=places_df)
        v0, _h0, h0 = self.__process_input__()
        _v1, v1, h1 = self.__reconstruction__(h0=h0)
        newWeights, newVisibleBiases, newHiddenBiases = self.__set_contrastive_divergence__(v0 = v0, h0 = h0, v1 = v1, h1 = h1, learning_rate = learning_rate)
        sumError = self.__set_error_function__(v0=v0, v1=v1)
        currentWeights, currentVisibleBiases, currentHiddenBiases, previousWeights, previousVisibleBiases, previousHiddenBiases = self.__init_tf_vars__()
        session = tf.Session()
        session.run(tf.global_variables_initializer())
        trainingListSize = len(training_list)
        errors_list = []
        for i in range(epochs):
            for start, end in zip(range(0, trainingListSize, batch_size), range(batch_size, trainingListSize, batch_size)):
                batch = training_list[start:end]
                currentWeights = session.run(newWeights, feed_dict={v0: batch, self.weights: previousWeights, self.visibleBiases: previousVisibleBiases, self.hiddenBiases: previousHiddenBiases})
                currentVisibleBiases = session.run(newVisibleBiases, feed_dict={v0: batch, self.weights: previousWeights, self.visibleBiases: previousVisibleBiases, self.hiddenBiases: previousHiddenBiases})
                currentHiddenBiases = session.run(newHiddenBiases, feed_dict={v0: batch, self.weights: previousWeights, self.visibleBiases: previousVisibleBiases, self.hiddenBiases: previousHiddenBiases})
                previousWeights = currentWeights
                previousVisibleBiases = currentVisibleBiases
                previousHiddenBiases = currentHiddenBiases
            errors_list.append(session.run(sumError, feed_dict={v0: training_list, self.weights: currentWeights, self.visibleBiases: currentVisibleBiases, self.hiddenBiases:currentHiddenBiases}))

        self.session = session
        self.v0 = v0
        self.previousWeights = previousWeights
        self.previousHiddenBiases = previousHiddenBiases
        self.previousVisibleBiases = previousVisibleBiases
        self.training_list = training_list
        self.__create_chart__(errors_list)
        return True

    def __get_user_position_by_id__(self, userId):
        try:
            pos = self.position_userId.index(userId)
            print "User position is %s" % pos
            return pos
        except ValueError:
            print "Error get user position"
            return -1
        
    def predict(self, places_df, userId, maxResults = 200):
        """Input Reconstruction"""
        userPosition = self.__get_user_position_by_id__(userId)
        if userPosition < 0:
            return pd.DataFrame()
        userId = [self.training_list[userPosition]]
        hh0 = tf.nn.sigmoid(tf.matmul(self.v0, self.weights) + self.hiddenBiases)
        vv1 = tf.nn.sigmoid(tf.matmul(hh0, tf.transpose(self.weights)) + self.visibleBiases)
        
        feed = self.session.run(hh0, feed_dict={self.v0: userId, self.weights: self.previousWeights, self.hiddenBiases: self.previousHiddenBiases})
        recommendation = self.session.run(vv1, feed_dict={hh0: feed, self.weights: self.previousWeights, self.visibleBiases: self.previousVisibleBiases})

        places_df['Recommendation Score'] = recommendation[0]
        result = places_df.sort_values(['Recommendation Score'], ascending=False).head(maxResults)
        #print result
        return result