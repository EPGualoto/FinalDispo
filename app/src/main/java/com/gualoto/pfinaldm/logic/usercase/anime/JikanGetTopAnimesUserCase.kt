package com.gualoto.pfinaldm.logic.usercase.anime

import android.util.Log
import com.gualoto.pfinaldm.data.core.getFullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.endpoints.AnimeEndPoints
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.repository.RetrofitBase


import java.lang.Exception

class JikanGetTopAnimesUserCase {

    suspend fun invoke(): Result<List<FullInfoAnimeLG>> {

        var result: Result<List<FullInfoAnimeLG>>? = null;
        val items = ArrayList<FullInfoAnimeLG>()

        try {
            val baseService = RetrofitBase.returnBaseRetrofitAnime()
            val service = baseService.create(AnimeEndPoints::class.java)
            val call = service.getAllTopAnimes()

            if (call.isSuccessful) {
                val infoAnime = call.body()!!
                infoAnime.data.forEach {
                    items.add(it.getFullInfoAnimeLG())
                }
                result = Result.success(items)
            } else {
                Log.e("TAG", "Error en el llamado de la Api de Jikan")
                result = Result.failure(Exception("Error en el llamado de la Api de Jikan"))
            }
        } catch (ex: Exception) {
            Log.e("TAG", ex.stackTraceToString())
            result = Result.failure(Exception(ex))
        }
        return result!!

    }

}