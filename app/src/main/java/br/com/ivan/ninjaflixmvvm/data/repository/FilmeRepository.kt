package br.com.ivan.ninjaflixmvvm.data.repository

import br.com.ivan.ninjaflixmvvm.data.entities.Credits
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeDetail
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeList
import br.com.ivan.ninjaflixmvvm.data.entities.VideoList
import br.com.ivan.ninjaflixmvvm.data.remote.FilmeRemoteDataSource
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmeRepository @Inject constructor(
    private val remoteDataSource: FilmeRemoteDataSource
) {

    suspend fun getFilmesPopular(page: Int = 1): Flow<NetworkResult<FilmeList>>{
        return flow {
            emit(remoteDataSource.getPopularFilmes())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailsFilme(id: Int): Flow<NetworkResult<FilmeDetail>>{
        return flow {
            emit(remoteDataSource.getFilmeDetails(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVideos(id:Int): Flow<NetworkResult<VideoList>>{
        return flow<NetworkResult<VideoList>> {
                emit(remoteDataSource.getVideos(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCredits(id:Int): Flow<NetworkResult<Credits>>{
        return flow {
            emit(remoteDataSource.getCredits(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcomingFilmes(): Flow<NetworkResult<FilmeList>>{
        return flow {
            emit(remoteDataSource.getUpcomingFilmes())
        }.flowOn(Dispatchers.IO)
    }

}