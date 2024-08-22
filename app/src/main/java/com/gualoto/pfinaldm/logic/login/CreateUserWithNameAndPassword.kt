package com.gualoto.pfinaldm.logic.login

import android.content.Context
import com.gualoto.pfinaldm.data.local.database.entities.UsersDB
import com.gualoto.pfinaldm.data.local.repository.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreateUserWithNameAndPassword(private val context: Context) {

    fun invoke(name: String, password: String) = flow<Result<Boolean>> {
        kotlinx.coroutines.delay(3000)
        val user = UsersDB(
            0, name, password
        )

        val con = DataBaseRepository.getDBConnection(context)
        con.getUserDao().saveUser(
            listOf(
                user
            )
        )

        emit(Result.success(true))
    }.catch { ex ->
        emit(Result.failure(ex))
    }.flowOn(Dispatchers.IO)
}
