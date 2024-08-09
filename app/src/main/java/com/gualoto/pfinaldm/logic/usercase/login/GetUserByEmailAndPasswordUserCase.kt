package com.gualoto.pfinaldm.logic.usercase.login

import com.gualoto.pfinaldm.data.firebase.FireStoreRepository
import com.gualoto.pfinaldm.ui.entities.users.UserLogin
import kotlinx.coroutines.flow.flow

class GetUserByEmailAndPasswordUserCase {
    suspend fun invoke(id:String) = flow<Result<UserLogin>>{
        FireStoreRepository().getUserById(id)
            .onSuccess {
                emit(Result.success(it.first()))
            }
            .onFailure {error->
                emit(Result.failure(error))
            }
    }
}