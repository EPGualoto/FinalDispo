package com.gualoto.pfinaldm.logic.login


import com.gualoto.pfinaldm.data.local.firebase.FireStoreRepository
import com.gualoto.pfinaldm.ui.entities.users.UserLogin

import kotlinx.coroutines.flow.flow


class SaveUserFireStoreUserCase {

    suspend fun invoke(user : UserLogin)= flow{
        val  x= FireStoreRepository().saveUserLogin(user)
            x.onSuccess {
                emit(it)
            }
            x.onFailure {
                emit(false)

            }
    }
}

