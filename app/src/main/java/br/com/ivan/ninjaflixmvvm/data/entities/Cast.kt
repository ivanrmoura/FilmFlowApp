package br.com.ivan.ninjaflixmvvm.data.entities

import com.google.gson.annotations.SerializedName

data class Cast(
    val adult: Boolean,
    val gender: Int,
    @SerializedName("known_for_department")
    val department: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("cast_id")
    val castId: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditId:String,
    val order: Int

)
