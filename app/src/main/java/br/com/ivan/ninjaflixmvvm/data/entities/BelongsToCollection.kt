package br.com.ivan.ninjaflixmvvm.data.entities

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("backdrop_path")
    val backdropPath: String
)
