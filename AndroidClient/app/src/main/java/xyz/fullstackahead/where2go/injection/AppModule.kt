package xyz.fullstackahead.where2go.injection

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fullstackahead.where2go.R
import xyz.fullstackahead.where2go.Where2GoApp
import xyz.fullstackahead.where2go.network.ApiClient
import xyz.fullstackahead.where2go.persistence.SharedPreferences
import javax.inject.Named
import javax.inject.Singleton

@Module class AppModule(private val application: Where2GoApp) {

    @Provides @Singleton
    fun provideApplicationContext(): Where2GoApp {
        return application
    }


    @Provides @Singleton
    fun provideApiClient(): ApiClient {
        val client = OkHttpClient.Builder()
        client.networkInterceptors().add(Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", SharedPreferences.currentUser.value?.token ?: "")
                    .removeHeader("Accept")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .build()
            chain.proceed(request)
        })

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
                .baseUrl(Where2GoApp.instance.getString(R.string.api_base_url))
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiClient::class.java)
    }

}
