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
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import retrofit2.Response
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.pojo.Recommendation
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.network.ApiClient
import xyz.fullstackahead.where2go.network.RequestManager
import xyz.fullstackahead.where2go.persistence.SharedPreferences
import xyz.fullstackahead.where2go.utils.getAddressFromLocation
import java.util.*
import javax.inject.Inject

class LandingViewModel(application: Application?) : AndroidViewModel(application), AIDialog.AIDialogListener {

    companion object {
        const val TAG = "LandingViewModel"
        const val ACTION_FIND_PLACES = "find-places"
        const val PARAM_CITY = "city"
        const val PARAM_CATEGORY = "category"
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

    var loadingCallback: (isLoading: Boolean) -> Unit = {}

    fun init(context: Context) {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(context).addApi(LocationServices.API).build()
            googleApiClient?.connect()
        }
    }


    fun getRecommendations() {
        /*val list = ArrayList<Recommendation>()
        (1..50).mapTo(list) {
            val rating = Random().nextInt(6).toFloat()
            Recommendation(
                    userRating = rating,
                    price = "€",
                    categories = listOf("Salads, Cookies"),
                    name = Where2GoApp.instance.getString(R.string.placeholder_title, it),
                    predictedRating = rating)
        }
        recommendations.postValue(list)*/

        loadingCallback.invoke(true)
        if (SharedPreferences.currentUser.value != null) {
            getRecommendations(null, null, {
                loadingCallback.invoke(false)
                if (it.isSuccessful) {
                    recommendations.postValue(it.body())
                } else {
                    Toast.makeText(Where2GoApp.instance, "Something went wrong, apologies", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Error response: " + it.toString())
                }
            })
        }
    }


    fun getRecommendations(city: String?, category: String?, callback: (response: Response<List<Recommendation>>) -> Unit) {
        RequestManager.execute(apiClient.getRecommendation(city, category), callback)
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


    fun getCity(): String? {
        return getLocation()?.let {
            val address = getAddressFromLocation(it.latitude, it.longitude, Where2GoApp.instance)
            return address?.adminArea
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
                var city: String? = null
                var category: String? = null
                if (result.result!!.parameters!!.containsKey(PARAM_CITY)) {
                    city = result.result.parameters[PARAM_CITY]?.asString
                }
                if (result.result!!.parameters!!.containsKey(PARAM_CATEGORY)) {
                    category = result.result.parameters[PARAM_CATEGORY]?.asString
                }
                loadingCallback.invoke(true)
                getRecommendations(city, category, {
                    loadingCallback.invoke(false)
                    if (it.isSuccessful) {
                        recommendations.postValue(it.body())
                    } else {
                        Toast.makeText(Where2GoApp.instance, "Something went wrong, apologies", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Error response: " + it.toString())
                    }
                })
            }
        }
    }

    override fun onError(error: AIError?) {
        Log.d(TAG, "API.AI - onResult")
    }
}
