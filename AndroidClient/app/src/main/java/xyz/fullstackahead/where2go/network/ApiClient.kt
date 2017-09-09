package xyz.fullstackahead.where2go.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import xyz.fullstackahead.where2go.pojo.User

interface ApiClient {

    @POST("/login")
    fun login(@Body user: User): Call<Void>


    //@GET("/api/v1/categories")
    //fun getCategories()

}
