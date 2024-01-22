package br.com.ivan.ninjaflixmvvm.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.ivan.ninjaflixmvvm.data.entities.Filme
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeList
import br.com.ivan.ninjaflixmvvm.data.remote.FilmeRemoteDataSource
import br.com.ivan.ninjaflixmvvm.data.remote.FilmeService
import br.com.ivan.ninjaflixmvvm.data.repository.FilmeRepository
import br.com.ivan.ninjaflixmvvm.utils.Constants
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "FilmePagingSource"

class FilmePagingSource @Inject constructor(
    private val filmeService: FilmeService,
    private val sourceData: String
): PagingSource<Int, Filme>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Filme> {

        return try {
            val currentPage = params.key ?: 1

            val response = when(sourceData){
                Constants.POPULAR_FILMES -> {
                    filmeService.getPopularFilmes(page = currentPage)
                }
                else -> {
                    filmeService.getTopRatedFilmes(page = currentPage)
                }
            }

            val resposeData = mutableListOf<Filme>()
            val data = response.body()?.results ?: emptyList()
            resposeData.addAll(data)
            LoadResult.Page(
                data = resposeData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception){
            Log.i(TAG, "load: $e")
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Filme>): Int? {
        return null
    }
}