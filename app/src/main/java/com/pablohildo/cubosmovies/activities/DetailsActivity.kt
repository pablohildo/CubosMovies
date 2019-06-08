package com.pablohildo.cubosmovies.activities

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pablohildo.cubosmovies.utils.ApiClient
import com.pablohildo.cubosmovies.utils.HttpConstants
import com.pablohildo.cubosmovies.R
import com.pablohildo.cubosmovies.models.MovieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity(), Callback<MovieModel> {

    lateinit var textOverview: TextView
    lateinit var imageMovie: ImageView
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        textOverview = findViewById(R.id.text_overview)
        imageMovie = findViewById(R.id.image_movie)
        context = this
        textOverview.movementMethod = ScrollingMovementMethod()
        val movieId:Int = intent.extras["id"].toString().toInt()
        ApiClient.apiInterface.getById(movieId,
            HttpConstants.API_KEY,
            HttpConstants.LANGUAGE
        ).enqueue(this)
    }

    override fun onFailure(call: Call<MovieModel>, t: Throwable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
        Toast.makeText(this,
            "Não foi possível obter os dados do filme. Por favor, verifique sua conexão",
            Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
        if(response.isSuccessful){
            textOverview.text = response.body()!!.overview
            Glide.with(context)
                .load(HttpConstants.IMAGE_BASE_URL_500 + response.body()!!.poster_path)
                .into(imageMovie)
            supportActionBar!!.title = response.body()!!.title
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
        return true
    }

}
