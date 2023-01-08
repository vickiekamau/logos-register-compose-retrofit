package com.droidcon.login.network

import com.droidcon.login.model.User
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/v1/users")
    suspend fun saveUser(@Body user: User): User

    @GET("api/v1/users")
    suspend fun getUser(): List<User>

    //We need to prepare a custom OkHttp client because need to use our custom call interceptor.
    // to be able to authenticate our requests.
    companion object {

        operator fun invoke(): ApiInterface {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create();


            val api = Retrofit.Builder() // Create retrofit builder.
                .baseUrl("http://41.139.209.115:8130/") // Base url for the api
                .addConverterFactory(GsonConverterFactory.create(gson)) // Use GSON converter for JSON to POJO object mapping.
                .client(client) // Here we set the custom OkHttp client we just created.
                .build()
                .create(ApiInterface::class.java) // We create an API using the interface we defined.
            return api

        }
    }
}