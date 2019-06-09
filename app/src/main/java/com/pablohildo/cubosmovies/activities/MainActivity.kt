package com.pablohildo.cubosmovies.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.pablohildo.cubosmovies.utils.ApiClient
import com.pablohildo.cubosmovies.utils.EndlessRecyclerViewScrollListener
import com.pablohildo.cubosmovies.utils.HttpConstants
import com.pablohildo.cubosmovies.R
import com.pablohildo.cubosmovies.adapters.RVAdapter
import com.pablohildo.cubosmovies.models.MovieListModel
import com.pablohildo.cubosmovies.models.MovieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<MovieListModel> {
    private lateinit var recyclerView: RecyclerView
    private var currentPage: Int = 1
    private var totalResults: MutableList<MovieModel> = mutableListOf()
    private lateinit var recyclerAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerAdapter = RVAdapter(this, totalResults)
        recyclerView.adapter = recyclerAdapter
        ApiClient.apiInterface.getAll(
            HttpConstants.API_KEY,
            HttpConstants.LANGUAGE, currentPage).enqueue(this)
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager as GridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                ApiClient.apiInterface.getAll(
                    HttpConstants.API_KEY,
                    HttpConstants.LANGUAGE, page).enqueue(this@MainActivity)
            }
        })
    }

    override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
        Toast.makeText(this,
            "Não foi possível obter os filmes. Por favor, verifique sua conexão",
            Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResponse(call: Call<MovieListModel>, response: Response<MovieListModel>) {
        if(response.isSuccessful){
            findViewById<ProgressBar>(R.id.progress_loading).visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            totalResults.addAll(response.body()!!.results!!)
            recyclerView.adapter!!.notifyItemRangeInserted(recyclerAdapter.itemCount, totalResults.size)
        }
    }

}
