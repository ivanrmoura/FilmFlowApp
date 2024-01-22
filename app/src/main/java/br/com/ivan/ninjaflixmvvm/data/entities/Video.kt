package br.com.ivan.ninjaflixmvvm.data.entities

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Video(
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val id: String
)
