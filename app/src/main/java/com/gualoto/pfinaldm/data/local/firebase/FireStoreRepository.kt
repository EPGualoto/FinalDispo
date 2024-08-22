package com.gualoto.pfinaldm.data.local.firebase

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.gualoto.pfinaldm.ui.entities.users.UserLogin
import kotlinx.coroutines.tasks.await


class FireStoreRepository {

    private  var  db:FirebaseFirestore = Firebase.firestore

    suspend fun saveUserLogin(user: UserLogin)= kotlin.runCatching{
        val id = db.collection("users")
            .add(user)
            .await()
            .id
        return@runCatching  true

    }

    suspend fun getUserById(id: String) = kotlin.runCatching{
        var items = arrayListOf<UserLogin>()
        db.collection("users") //a mi nombre de la consulta en FIRESTORE de databse
            .whereEqualTo("uuid",id)
            .get()
            .await()
            .forEach{
                items.add(it.toObject<UserLogin>())

            }
        return@runCatching items

    }
}