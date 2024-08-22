package com.gualoto.pfinaldm.ui.viewmodels.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.data.network.entities.clubs.fullClub.Data
import com.gualoto.pfinaldm.data.network.repository.RetrofitInstance
import kotlinx.coroutines.launch

class ClubSearchViewModel : ViewModel() {
    private val _clubs = MutableLiveData<List<Data>>()
    val clubs: LiveData<List<Data>> get() = _clubs

    fun getAllClubs() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getClubs(page = 1) // Sin query, obteniendo todos
                if (response.isSuccessful) {
                    _clubs.value = response.body()?.data ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }
    fun searchClubs(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getClubs(query, 1) // Pasar ambos parámetros: query y page
                if (response.isSuccessful) {
                    _clubs.value = response.body()?.data ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }

}
