package br.com.ivan.ninjaflixmvvm.ui.filmedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ivan.ninjaflixmvvm.data.entities.Credits
import br.com.ivan.ninjaflixmvvm.data.entities.FilmeDetail
import br.com.ivan.ninjaflixmvvm.data.entities.VideoList
import br.com.ivan.ninjaflixmvvm.data.repository.FilmeRepository
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmeDetailsViewModel @Inject constructor(
    private val filmeRepository: FilmeRepository
): ViewModel() {

    private val _filmeDetailsState = MutableLiveData<NetworkResult<FilmeDetail>>()
    val filmeDetailsState = _filmeDetailsState

    private val _videosState = MutableLiveData<NetworkResult<VideoList>>()
    val videosState = _videosState

    private val _creditsState = MutableLiveData<NetworkResult<Credits>>()
    val creditsState = _creditsState

    fun getFilmeDetails(id: Int){
        viewModelScope.launch {
            filmeRepository.getDetailsFilme(id).collect{ values ->
                _filmeDetailsState.value = values
            }
        }
    }

    fun getVideos(id: Int){
        viewModelScope.launch {
            filmeRepository.getVideos(id).collect { values ->
                _videosState.value = values
            }
        }
    }

    fun getCredits(id: Int){
        viewModelScope.launch {
            filmeRepository.getCredits(id).collect{ values ->
                _creditsState.value = values
            }
        }
    }


}