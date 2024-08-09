package com.gualoto.pfinaldm.logic.usercase.anime

import android.util.Log
import com.gualoto.pfinaldm.data.core.getFullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.endpoints.AnimeEndPoints
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.repository.RetrofitBase
import java.lang.Exception

class GetAnimeTopUserCase {

    suspend fun getFullAnimeInfo(nameAnime:Int): Result<FullInfoAnimeLG> {

        var result:Result<FullInfoAnimeLG>?=null
        var infoAnime: FullInfoAnimeLG = FullInfoAnimeLG()
        try {
            val baseService= RetrofitBase.returnBaseRetrofitAnime()
            val service= baseService.create(AnimeEndPoints::class.java) //creo mi servicio
            val call= service.getAnimeFullInformation(nameAnime) //ahora si podria acceder a travez de servicio a los metodos
            //me revuelve un response de FullInfoAnime


            Log.d("TAG", "antes de call.isSu..: "+call)
            if(call.isSuccessful){
                Log.d("TAG", "call: "+call)
                val a=call.body()!!
                infoAnime=a.getFullInfoAnimeLG()


                result = Result.success(infoAnime)
                Log.d("TAG", "result: "+result)
            }else{
                Log.d("TAG", "Error en el llamado a la API de Jikan")
                result=Result.failure(Exception("Error en el llamado a la API de Jikan"))
            }
        }catch (ex: Exception){
            Log.e("TAG","catch: " +ex.stackTraceToString())
            result=Result.failure(Exception(ex))
        };
        return result!!
    }



}