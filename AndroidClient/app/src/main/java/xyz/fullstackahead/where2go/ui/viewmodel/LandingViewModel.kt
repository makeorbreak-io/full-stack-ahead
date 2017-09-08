package xyz.fullstackahead.where2go.ui.viewmodel

import ai.api.model.AIError
import ai.api.model.AIResponse
import ai.api.ui.AIButton
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log

class LandingViewModel(application: Application?) : AndroidViewModel(application), AIButton.AIButtonListener {

    companion object {
        const val TAG = "LandingViewModel"
    }


    val apiResponse: MutableLiveData<String> = MutableLiveData()


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
