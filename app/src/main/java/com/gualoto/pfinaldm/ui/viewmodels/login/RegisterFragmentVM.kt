package com.gualoto.pfinaldm.ui.viewmodels.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.logic.usercase.login.CreateUserWithNameAndPassword
import com.gualoto.pfinaldm.ui.core.UIStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragmentVM: ViewModel() {

    var uiState = MutableLiveData<UIStates>()

    fun saveUser(name: String, password: String, context: Context) {
        viewModelScope.launch {
            uiState.postValue(UIStates.Loading(true))
            CreateUserWithNameAndPassword(context).invoke(name, password)
                .collect {
                    it.onSuccess {
                        uiState.postValue(UIStates.Success(it))
                    }
                    it.onFailure {
                        uiState.postValue(UIStates.Error(it.message.toString()))
                    }
                }
            delay(500)
            uiState.postValue(UIStates.Loading(false))
        }
    }
}
