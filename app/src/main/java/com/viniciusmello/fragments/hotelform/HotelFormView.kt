package com.viniciusmello.fragments.hotelform

import com.viniciusmello.fragments.entities.Hotel

interface HotelFormView {

    fun showHotel(hotel: Hotel)
    fun showErrorSaveHotel()
    fun showErrorValidateHotel()

}