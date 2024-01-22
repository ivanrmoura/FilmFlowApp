package br.com.ivan.ninjaflixmvvm.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ivan.ninjaflixmvvm.data.repository.FirebaseAuthRepositoryImpl
import br.com.ivan.ninjaflixmvvm.domain.model.User
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepositoryImpl
): ViewModel() {

    private val _logInState = MutableLiveData<NetworkResult<Boolean>>()
    val logInState = _logInState

    private val _logOutState = MutableLiveData<NetworkResult<Boolean>>()
    val logOutState = _logOutState

    private val _getDataState = MutableLiveData<NetworkResult<User>>()
    val getDataState = _getDataState


    fun getDataUser(){
        viewModelScope.launch {
            authRepository.getUserData().collect{value ->
                _getDataState.value = value
            }
        }
    }

    fun logIn(email: String, password: String){
        viewModelScope.launch {
            authRepository.login(email, password).collect{value ->
                _logInState.value = value
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            authRepository.logOut().collect{value ->
                _logOutState.value = value
            }
        }
    }


}