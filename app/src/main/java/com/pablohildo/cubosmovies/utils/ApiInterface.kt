package com.pablohildo.cubosmovies.utils

import com.pablohildo.cubosmovies.models.MovieListModel
import com.pablohildo.cubosmovies.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("popular?")
    fun getAll(@Query("api_key") api_key: String,
               @Query("language") language: String,
               @Query("page") page: Int): Call<MovieListModel>

    @GET("{id}?")
    fun getById(@Path("id") id: Int,
                @Query("api_key") api_key: String,
                @Query("language") language: String): Call<MovieModel>
}