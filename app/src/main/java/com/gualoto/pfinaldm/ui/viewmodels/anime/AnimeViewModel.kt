package com.gualoto.pfinaldm.ui.viewmodels.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.data.network.repository.RetrofitInstance
import com.gualoto.pfinaldm.ui.core.TopAnimeUI
import com.gualoto.pfinaldm.ui.core.toTopAnimeUI
import com.gualoto.pfinaldm.ui.core.SeasonAnimeUI
import com.gualoto.pfinaldm.ui.core.SeasonUAnimeUI
import com.gualoto.pfinaldm.ui.core.toSeasonUAnimeUI
import com.gualoto.pfinaldm.ui.core.toSeasonAnimeUI
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val _seasonNowAnimes = MutableLiveData<List<SeasonAnimeUI>>()
    val seasonNowAnimes: LiveData<List<SeasonAnimeUI>> = _seasonNowAnimes

    private val _seasonUpcomingAnimes = MutableLiveData<List<SeasonUAnimeUI>>()
    val seasonUpcomingAnimes: LiveData<List<SeasonUAnimeUI>> = _seasonUpcomingAnimes

    private val _topAnimes = MutableLiveData<List<TopAnimeUI>>()
    val topAnimes: LiveData<List<TopAnimeUI>> = _topAnimes

    fun getSeasonUpcomingAnimes() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSeasonUpcomingAnimes()
                if (response.isSuccessful) {
                    val seasonAnimes = response.body()?.data?.take(20)?.map { it.toSeasonUAnimeUI() } ?: emptyList()
                    _seasonUpcomingAnimes.postValue(seasonAnimes)
                } else {
                    Log.e("AnimeViewModel", "Error fetching top animes: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error fetching top animes", e)
            }
        }
    }

    fun getSeasonAnimes() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSeasonNowAnimes()
                if (response.isSuccessful) {
                    val seasonA = response.body()?.data?.take(10)?.map { it.toSeasonAnimeUI() } ?: emptyList()
                    _seasonNowAnimes.postValue(seasonA)
                } else {
                    Log.e("AnimeViewModel", "Error fetching top animes: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error fetching top animes", e)
            }
        }
    }

    fun getTopAnimes() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopAnimes()
                if (response.isSuccessful) {
                    val topAnimes = response.body()?.data?.take(10)?.map { it.toTopAnimeUI() } ?: emptyList()
                    _topAnimes.postValue(topAnimes)
                } else {
                    Log.e("AnimeViewModel", "Error fetching top animes: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error fetching top animes", e)
            }
        }
    }
}