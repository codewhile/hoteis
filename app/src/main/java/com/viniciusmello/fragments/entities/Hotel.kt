package com.viniciusmello.fragments.entities

data class Hotel(
    var id:Long = 0L ,
    var nome:String = "",
    var adress:String = "",
    var rating:Float = 0.0F
) {
    override fun toString(): String = this.nome
}
