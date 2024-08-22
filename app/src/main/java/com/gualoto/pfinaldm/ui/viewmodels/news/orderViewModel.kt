package com.gualoto.pfinaldm.ui.viewmodels.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _order = MutableLiveData<String>()
    val order: LiveData<String> get() = _order

    init {
        _order.value = "recent-newest"  // Valor inicial
    }

    fun setOrder(order: String) {
        _order.value = order
    }

}
