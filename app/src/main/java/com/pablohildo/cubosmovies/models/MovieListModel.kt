package com.pablohildo.cubosmovies.models

class MovieListModel {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: List<MovieModel>? = null
}