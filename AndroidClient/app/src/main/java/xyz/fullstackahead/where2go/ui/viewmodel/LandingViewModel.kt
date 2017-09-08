package xyz.fullstackahead.where2go.ui.viewmodel

import ai.api.model.AIError
import ai.api.model.AIResponse
import ai.api.ui.AIButton
import ai.api.ui.AIDialog
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Recommendation
import xyz.fullstackahead.where2go.Where2GoApp
import java.util.*

class LandingViewModel(application: Application?) : AndroidViewModel(application), AIDialog.AIDialogListener {

    companion object {
        const val TAG = "LandingViewModel"
    }

    val apiResponse: MutableLiveData<String> = MutableLiveData()
    val recommendations: MutableLiveData<List<Recommendation>> = MutableLiveData()


    fun getRecommendations() {
        // TODO
        val list = ArrayList<Recommendation>()
        (1..50).mapTo(list) {
            Recommendation(
                    getApplication<Where2GoApp>().getString(R.string.placeholder_title, it),
                    getApplication<Where2GoApp>().getString(R.string.placeholder_description),
                    "",
                    Random().nextInt(5))
        }
        recommendations.postValue(list)
    }


    // AIButtonListener Callbacks
    override fun onCancelled() {
        Log.d(TAG, "API.AI - onCancelled")
    }

    override fun onResult(result: AIResponse?) {
        Log.d(TAG, "API.AI - onResult")
        result?.result?.fulfillment?.speech?.let { apiResponse.postValue(it) }
    }

    override fun onError(error: AIError?) {
        Log.d(TAG, "API.AI - onResult")
    }
}
