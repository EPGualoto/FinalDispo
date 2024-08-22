package com.gualoto.pfinaldm.ui.viewmodels.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.data.network.entities.clubs.members.Data
import com.gualoto.pfinaldm.data.network.repository.RetrofitInstance
import kotlinx.coroutines.launch

class ClubMembersViewModel : ViewModel() {
    private val _members = MutableLiveData<List<Data>>()
    val members: LiveData<List<Data>> get() = _members

    fun getClubMembers(clubId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getClubMembers(clubId)
                if (response.isSuccessful) {
                    _members.value = response.body()?.data ?: emptyList()
                } else {
                    // Manejar error de respuesta
                }
            } catch (e: Exception) {
                // Manejar excepción
            }
        }
    }
}
