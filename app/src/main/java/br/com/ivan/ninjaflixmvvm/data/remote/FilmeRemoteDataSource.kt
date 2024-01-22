package br.com.ivan.ninjaflixmvvm.data.remote

import br.com.ivan.ninjaflixmvvm.utils.Constants
import javax.inject.Inject

class FilmeRemoteDataSource @Inject constructor(
    private val filmeService: FilmeService
): BaseApiResponse() {

    suspend fun getPopularFilmes(page: Int = 1) = safeApiCall {filmeService.getPopularFilmes(page=page)}

    suspend fun getFilmeDetails(id: Int) = safeApiCall { filmeService.getFilmeDetail(id=id)}

    suspend fun getVideos(id: Int) = safeApiCall { filmeService.getFilmeVideos(id=id)}

    suspend fun getCredits(id: Int) = safeApiCall { filmeService.getFilmeCredits(id=id)}

    suspend fun getUpcomingFilmes(page: Int = 1) = safeApiCall {filmeService.getFilmeUpcoming(page=page)}


}