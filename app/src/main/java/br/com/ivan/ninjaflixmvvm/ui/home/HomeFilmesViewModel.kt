package br.com.ivan.ninjaflixmvvm.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.ivan.ninjaflixmvvm.data.entities.Filme
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeList
import br.com.ivan.ninjaflixmvvm.data.remote.FilmeService
import br.com.ivan.ninjaflixmvvm.data.repository.FilmeRepository
import br.com.ivan.ninjaflixmvvm.paging.FilmePagingSource
import br.com.ivan.ninjaflixmvvm.utils.Constants
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 50

@HiltViewModel
class HomeFilmesViewModel @Inject constructor(
    private val filmeRepository: FilmeRepository,
    private val filmeService: FilmeService
): ViewModel() {

    private val _upcomingFilmesState = MutableLiveData<NetworkResult<FilmeList>>()
    val upcomingFilmesState = _upcomingFilmesState

    fun getUpcomingFilmes(){
        viewModelScope.launch {
            filmeRepository.getUpcomingFilmes().collect{values ->
                _upcomingFilmesState.value = values
            }
        }
    }

    val popularFilmesListData = Pager(
        config = PagingConfig(pageSize = 1),
        pagingSourceFactory = {FilmePagingSource (filmeService, Constants.POPULAR_FILMES)   }
    ).flow.cachedIn(viewModelScope)

    val topRatedFilmesListData = Pager(
        config = PagingConfig(pageSize = 1),
        pagingSourceFactory = {FilmePagingSource (filmeService, Constants.TOP_RATED_FILMES )   }
    ).flow.cachedIn(viewModelScope)



}