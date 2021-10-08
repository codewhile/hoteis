package com.viniciusmello.fragments.hotelist

import com.viniciusmello.fragments.entities.Hotel

interface HotelListView {
    fun showHotels(hotels: List<Hotel>)
    fun showHotelsDetails(hotel: Hotel)
    fun hideDeleteMode()
    fun showDeleteMode()
    fun showSelectedHotels(hotels: MutableList<Hotel>)
    fun showCounterSelectedHotels(count: Int)
    fun showSnackBarUndoDelete()


}