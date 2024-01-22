package br.com.ivan.ninjaflixmvvm.ui.signup

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ivan.ninjaflixmvvm.data.repository.FirebaseAuthRepositoryImpl
import br.com.ivan.ninjaflixmvvm.domain.model.User
import br.com.ivan.ninjaflixmvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepositoryImpl
): ViewModel() {


    private val _signUpState = MutableLiveData<NetworkResult<User>>()
    val signUpState = _signUpState

    private val _saverUserState = MutableLiveData<NetworkResult<Boolean>>()
    val saveUserState = _saverUserState



    fun signUp(user: User, password: String){
        viewModelScope.launch {
            authRepository.signUp(user, password)
                .collect{ value ->
                    _signUpState.value = value
                }
        }
    }


    fun saveUser(user: User, uri: Uri){
        viewModelScope.launch {
            authRepository.saveUser(user, uri).collect{ value->
                _saverUserState.value = value
            }
        }
    }



}
