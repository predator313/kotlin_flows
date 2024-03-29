package com.aamirashraf.stateflows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FlowViewModel:ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState:StateFlow<LoginUiState> = _loginUiState

    //the important property of the sealed class is that it only allowed to be inherit from the class
    //inside it
    fun login(userName:String,password:String)=viewModelScope.launch {
        _loginUiState.value=LoginUiState.Loading
        delay(2000L)
        if(userName=="Android" && password=="abc"){
            _loginUiState.value=LoginUiState.Success
        }
        else{
            _loginUiState.value=LoginUiState.Error("Wrong credentials")
        }
    }

    sealed class LoginUiState{
        object Success:LoginUiState()
        data class Error(val message:String):LoginUiState()
        object Loading:LoginUiState()
        object Empty:LoginUiState()
    }
}