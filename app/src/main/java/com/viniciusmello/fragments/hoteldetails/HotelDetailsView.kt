package com.viniciusmello.fragments.hoteldetails

import com.viniciusmello.fragments.entities.Hotel

interface HotelDetailsView {

    fun showHotel(hotel: Hotel)
    fun showErrorHotelNotFound()

}