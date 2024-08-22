package com.gualoto.pfinaldm.ui.entities.users

data class UserLogin (val uuid:String,
                        val name:String,
                       val lastName:String){
    constructor() : this("", "", "")
}