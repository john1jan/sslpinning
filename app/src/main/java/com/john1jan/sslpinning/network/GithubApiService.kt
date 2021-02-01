package com.john1jan.sslpinning.network

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://api.github.com/"


private val certPinner = CertificatePinner.Builder()
    .add("api.github.com", "sha256/ORtIOYkm5k6Nf2tgAK/uwftKfNhJB3QS0Hs608SiRmE=")
    .build()


private fun setupInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = (HttpLoggingInterceptor.Level.BASIC)
    return logging
}


private val okHttpClient = OkHttpClient.Builder()
    .certificatePinner(certPinner)
    .addInterceptor(setupInterceptor())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface GithubApiService {
    @GET("/users/{profile}")
    fun getUserData(@Path("profile") profile: String):
            Call<GithubUser>
}

object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}