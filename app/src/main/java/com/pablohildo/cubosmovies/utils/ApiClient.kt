package com.pablohildo.cubosmovies.utils

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(HttpConstants.MOVIE_BASE_URL)
        .build()
    val apiInterface: ApiInterface = retrofit.create(
        ApiInterface::class.java)
    private val DEFAULT_TIMEOUT = 5L
}