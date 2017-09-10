# Full Stack Ahead presents Where2.Go

A recommendation engine that learns based on your interests and the interests of other users. Currently working with restaurants and other food-related businesses, based on data returned by the [Yelp public API](https://www.yelp.com/developers/documentation/v3).

Where2.Go consists of a native Android app, which serves as the frontend for a core API and an AI service.

## Android app
* Native app built using [Kotlin](https://kotlinlang.org/)
* Uses several state of the art "standard" libraries, for example:
   * [Dagger2](https://github.com/google/dagger) for dependency injection
   * [Retrofit](https://github.com/square/retrofit) as the HTTP client
   * [Google Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) (still in alpha)
   * [API.AI](https://api.ai) for speech-to-text and natural language processing (NLP)
* For time saving purposes, the app is only available for smartphones running Android 5.0 and above
* Uses the device's location to suggest places near the user by default
* NLP allows for easy hands-free operation
* UI built following Google's [Material Design](https://material.io/guidelines/) guidelines

## Core API
* Built using [Elixir](https://elixir-lang.org/)
* Serves as the backend for the Android app, using the [Phoenix framework](http://phoenixframework.org/)
* Deployed on a virtual machine using [Docker](https://www.docker.com/)
* Maintains a [MariaDB](https://mariadb.org/) database which stores user preferences and profiles
* Integrates with the [Yelp API](https://www.yelp.com/developers/documentation/v3)

## AI Recommendation Service
* Built using [Python](https://www.python.org/), with [Tensorflow](https://www.tensorflow.org/) for the heavy lifting
* Has a small API built with [Flask](http://flask.pocoo.org/), to allow the Core API to request recommendations
* Uses a [Restricted Boltzmann Machine](https://en.wikipedia.org/wiki/Restricted_Boltzmann_machine) convolutional neural network to build a preference tree of the various users
* Uses a Sigmoid function as an activation function, since they "compress" the input to fit in an interval

_____

Built by Full Stack Ahead:
* [Bruno](https://www.linkedin.com/in/bruno-pina-447536a0)
* [Gonçalo](https://www.linkedin.com/in/goncalojoaocorreia/)
* [Ricardo](https://www.linkedin.com/in/ricardo-almeida-507b1183/)
* [Tiago](https://www.linkedin.com/in/tmrocha/)
