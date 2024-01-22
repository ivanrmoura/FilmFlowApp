package br.com.ivan.ninjaflixmvvm.data.entities

import com.google.gson.annotations.SerializedName

data class FilmeList(
    val page: Int,
    val results: List<Filme>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)