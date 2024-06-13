package com.mfo.teambalancerapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfo.teambalancerapp.domain.model.LoginRequest
import com.mfo.teambalancerapp.domain.usecase.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val postLoginUseCase: PostLoginUseCase): ViewModel() {
    private var _state = MutableStateFlow<LoginState>(LoginState.Loading)
    val state: StateFlow<LoginState> = _state

    fun authenticationUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val result = withContext(Dispatchers.IO) { postLoginUseCase(loginRequest) }
                if(result != null) {
                    _state.value = LoginState.Success(result.token)
                } else {
                    _state.value = LoginState.Error("Error Occurred, please try again later. ")
                }
            } catch (e: Exception) {
                val errorMessage: String = e.message.toString()
                _state.value = LoginState.Error(errorMessage)
            }
        }
    }
}