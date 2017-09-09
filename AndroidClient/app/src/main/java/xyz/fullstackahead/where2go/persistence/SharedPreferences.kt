package xyz.fullstackahead.where2go.persistence

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.pojo.User

object SharedPreferences {

    private const val PREFERENCES = "xyz.fullstackahead.where2go.SHARED_PREFERENCES"
    private const val KEY_USER = "user"

    val currentUser: MutableLiveData<User?> = MutableLiveData()

    private val sharedPreferences: SharedPreferences
    private val gson = Gson()

    init {
        sharedPreferences = Where2GoApp.instance.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        currentUser.postValue(getCurrentUser())
    }


    private fun getCurrentUser(): User? {
        val serialized = sharedPreferences.getString(KEY_USER, null)
        return serialized?.let { gson.fromJson(serialized, User::class.java) }
    }


    fun storeCurrentUser(user: User?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER, gson.toJson(user))
        editor.apply()
        currentUser.postValue(user)
    }

}