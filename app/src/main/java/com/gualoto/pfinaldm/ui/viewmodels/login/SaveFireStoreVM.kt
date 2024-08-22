package com.gualoto.pfinaldm.ui.viewmodels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.logic.login.GetUserByEmailAndPasswordUserCase
import com.gualoto.pfinaldm.logic.login.SaveUserFireStoreUserCase
import com.gualoto.pfinaldm.ui.core.UIStates
import com.gualoto.pfinaldm.ui.entities.users.UserLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SaveFireStoreVM: ViewModel() {

     val userUI get() = _userUI  //Esta es inmutable y le hace referencai la de abajo
    private var _userUI= MutableLiveData<UIStates>() //Esta variable es local


    val userLogin get() = _userLogin
    private var _userLogin= MutableLiveData<UserLogin>()


    //Con esto nadie la modifica

    fun saveUserFireStore(user: UserLogin) {
        viewModelScope.launch {
            SaveUserFireStoreUserCase().invoke(user)

                .collect {
                    if (it) {
                        _userUI.postValue(UIStates.Success(true))
                    } else {
                        _userUI.postValue(
                            UIStates.Error(
                                "Ocurrio un Error al Generar" +
                                        " la PETICION, INTENTELO MAS TARDE"
                            )
                        )
                    }


                }
        }
    }


        fun getUserByIdFireStore(id:String){
            _userUI.postValue(UIStates.Loading(true))
            viewModelScope.launch {

                GetUserByEmailAndPasswordUserCase().invoke(id)
                    .collect{resp->
                        resp.onSuccess {
                            _userLogin.postValue(it)

                        }
                        resp.onFailure {
                            _userUI.postValue(UIStates.Error(it.message.toString()))
                        }

                    }
                delay(500)
                _userUI.postValue(UIStates.Loading(false))

            }

        }

    }
