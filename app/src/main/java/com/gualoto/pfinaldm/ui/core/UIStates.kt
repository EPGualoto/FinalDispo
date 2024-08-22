package com.gualoto.pfinaldm.ui.core

sealed class UIStates {
    class Success(val condition:Boolean):UIStates()
    class Error(val message:String):UIStates()
    class  Loading( val isLoading: Boolean):UIStates()
}