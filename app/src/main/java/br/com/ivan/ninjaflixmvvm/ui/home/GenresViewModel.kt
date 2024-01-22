package br.com.ivan.ninjaflixmvvm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ivan.ninjaflixmvvm.data.entities.Genres
import br.com.ivan.ninjaflixmvvm.data.entities.GenresList
import br.com.ivan.ninjaflixmvvm.data.repository.GenresRepository
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val genresRepository: GenresRepository
): ViewModel() {

    private val _getGenresState = MutableLiveData<NetworkResult<GenresList>>()
    val getGenresState = _getGenresState

    fun getGenres(){
        viewModelScope.launch {
            genresRepository.getGenres().collect{ values ->
                _getGenresState.value = values
            }
        }
    }

}