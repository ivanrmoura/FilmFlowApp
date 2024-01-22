package br.com.ivan.ninjaflixmvvm.data.repository

import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.data.entities.GenresList
import br.com.ivan.ninjaflixmvvm.data.remote.BaseApiResponse
import br.com.ivan.ninjaflixmvvm.data.remote.GenresService
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val genresService: GenresService
) :BaseApiResponse() {

    fun getGenres(): Flow<NetworkResult<GenresList>>{
        return flow {
             emit(safeApiCall { genresService.getGenres() })
        }.flowOn(Dispatchers.IO)
    }

}