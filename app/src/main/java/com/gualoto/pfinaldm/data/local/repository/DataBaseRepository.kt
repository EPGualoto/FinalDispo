package com.gualoto.pfinaldm.data.local.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gualoto.pfinaldm.data.local.database.dao.UsersDAO
import com.gualoto.pfinaldm.data.local.database.entities.UsersDB
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [UsersDB::class],
    version = 1
)
abstract class DataBaseRepository : RoomDatabase() {
    abstract fun getUserDao(): UsersDAO

    companion object {

      @Volatile
        private var dbConnection: DataBaseRepository? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDBConnection(context: Context): DataBaseRepository {
            return  if(dbConnection ==null){
                kotlinx.coroutines.internal.synchronized(this){
                    val INSTANCE =
                        Room.databaseBuilder(
                            context,
                            DataBaseRepository::class.java,
                            "Datos"
                        ).build()
                    dbConnection =INSTANCE
                    dbConnection!!
                }
            } else{
                 dbConnection!!
            }

        }
    }
}