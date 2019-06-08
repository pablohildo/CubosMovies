package com.pablohildo.cubosmovies.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pablohildo.cubosmovies.activities.DetailsActivity
import com.pablohildo.cubosmovies.utils.HttpConstants
import com.pablohildo.cubosmovies.R
import com.pablohildo.cubosmovies.models.MovieModel

class RVAdapter(private val context: Context, private val movieList: List<MovieModel>):
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textMovieTitle.text = movieList[position].title
        Glide.with(context)
            .load(HttpConstants.IMAGE_BASE_URL_342 + movieList[position].poster_path)
            .into(holder.imageMovie)
        holder.cardMovie.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id", movieList[position].id)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                holder.imageMovie,
                "movie_image"
            )
            context.startActivity(intent, options.toBundle())
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val textMovieTitle: TextView = v.findViewById(R.id.text_movie_title)
        val imageMovie: ImageView = v.findViewById(R.id.image_movie)
        val cardMovie: CardView = v.findViewById(R.id.card_movie)
    }
}