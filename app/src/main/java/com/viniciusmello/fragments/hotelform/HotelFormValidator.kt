package com.viniciusmello.fragments.hotelform

import com.viniciusmello.fragments.entities.Hotel

class HotelFormValidator {


    companion object {
        fun validate(hotel: Hotel): Boolean = with(hotel)  {
            checkName(this.nome) && checkAdress(hotel.adress)
        }

        private fun checkName(nome: String): Boolean  =
            nome.length in  2..40

        private fun checkAdress(adress: String): Boolean =
            adress.length in 2..80
    }

}