package xyz.fullstackahead.where2go.ui.viewmodel

import ai.api.model.AIError
import ai.api.model.AIResponse
import ai.api.ui.AIDialog
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.pojo.Recommendation
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.network.ApiClient
import xyz.fullstackahead.where2go.network.RequestManager
import java.util.*
import javax.inject.Inject

class LandingViewModel(application: Application?) : AndroidViewModel(application), AIDialog.AIDialogListener {

    companion object {
        const val TAG = "LandingViewModel"
        const val ACTION_FIND_PLACES = "find-places"
        const val PARAM_CITY = "city"
    }

    @Inject
    lateinit var apiClient: ApiClient

    init {
        Where2GoApp.instance.component.inject(this)
    }

    val apiResponse: MutableLiveData<String> = MutableLiveData()
    val recommendations: MutableLiveData<List<Recommendation>> = MutableLiveData()
    val categories: MutableLiveData<List<String>> = MutableLiveData()

    private var googleApiClient: GoogleApiClient? = null

    fun init(context: Context) {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(context).addApi(LocationServices.API).build()
            googleApiClient?.connect()
        }
    }


    fun getRecommendations() {
        // TODO network call
        val list = ArrayList<Recommendation>()
        (1..50).mapTo(list) {
            val rating = Random().nextInt(6).toFloat()
            Recommendation(
                    userRating = rating,
                    price = "€",
                    categories = listOf("Salads, Cookies"),
                    name = Where2GoApp.instance.getString(R.string.placeholder_title, it),
                    predictedRating = rating)
        }
        recommendations.postValue(list)
    }


    fun getCategories() {
        RequestManager.execute(apiClient.getCategories(), {
            if (it.isSuccessful) {
                categories.postValue(it.body())
            }
        })
    }


    fun getLocation(): Location? {
        return try {
            LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        } catch (ex: SecurityException) {
            Log.d(TAG, ex.message)
            null
        }
    }


    // AIButtonListener Callbacks
    override fun onCancelled() {
        Log.d(TAG, "API.AI - onCancelled")
    }

    override fun onResult(result: AIResponse?) {
        Log.d(TAG, "API.AI - onResult")
        result?.result?.fulfillment?.speech?.let { apiResponse.postValue(it) }

        when (result?.result?.action) {
            ACTION_FIND_PLACES -> {
                val city = result.result?.parameters?.get(PARAM_CITY)?.asString
                // TODO: getRecommendations(city)
            }
        }
    }

    override fun onError(error: AIError?) {
        Log.d(TAG, "API.AI - onResult")
    }
}
