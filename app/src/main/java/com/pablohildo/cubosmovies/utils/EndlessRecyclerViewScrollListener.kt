package com.pablohildo.cubosmovies.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

abstract class EndlessRecyclerViewScrollListener(layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {
    private var visibleTreshold = 20
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private var startingPageIndex = 0
    var mLayoutManager = layoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        var totalItemCount = mLayoutManager.itemCount
        if(totalItemCount < previousTotalItemCount){
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if(totalItemCount == 0) this.loading = true
        }

        if(this.loading && (totalItemCount > previousTotalItemCount)){
            this.loading = false
            this.previousTotalItemCount = totalItemCount
        }

        if(!this.loading && (lastVisibleItemPosition+visibleTreshold) > totalItemCount){
            this.currentPage++
            onLoadMore(currentPage, totalItemCount)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
}