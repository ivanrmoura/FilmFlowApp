package br.com.ivan.ninjaflixmvvm.data.remote

import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.data.entities.GenresList
import br.com.ivan.ninjaflixmvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKei: String = Constants.API_KEY
    ): Response<GenresList>
}