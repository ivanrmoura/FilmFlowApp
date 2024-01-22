package br.com.ivan.ninjaflixmvvm.data.remote

import br.com.ivan.ninjaflixmvvm.data.entities.Credits
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeDetail
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeList
import br.com.ivan.ninjaflixmvvm.data.entities.VideoList
import br.com.ivan.ninjaflixmvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmeService {
    @GET("movie/popular")
    suspend fun getPopularFilmes(
        @Query("api_key") apiKei: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<FilmeList>

    @GET("movie/top_rated")
    suspend fun getTopRatedFilmes(
        @Query("api_key") apiKei: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<FilmeList>

    @GET("movie/{id}")
    suspend fun getFilmeDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKei: String = Constants.API_KEY
    ): Response<FilmeDetail>

    @GET("movie/{id}/videos")
    suspend fun getFilmeVideos(
        @Path("id") id: Int,
        @Query("api_key") apiKei: String = Constants.API_KEY
    ): Response<VideoList>

    @GET("movie/{id}/credits")
    suspend fun getFilmeCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKei: String = Constants.API_KEY
    ): Response<Credits>


    @GET("movie/upcoming")
    suspend fun getFilmeUpcoming(
        @Query("api_key") apiKei: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<FilmeList>

}