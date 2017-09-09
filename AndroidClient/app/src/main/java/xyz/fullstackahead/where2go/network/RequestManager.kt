package xyz.fullstackahead.where2go.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RequestManager {

    fun <T : Any> enqueue(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {}
            override fun onFailure(call: Call<T>?, t: Throwable?) {}
        })
    }


    fun <T : Any> execute(call: Call<T>, onSuccess: (response: Response<T>) -> Unit) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                response?.let(onSuccess)
            }

            override fun onFailure(call: Call<T>?, t: Throwable?) {}
        })
    }
}