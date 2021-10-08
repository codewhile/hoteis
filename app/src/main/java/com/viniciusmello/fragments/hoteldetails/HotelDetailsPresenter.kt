package com.viniciusmello.fragments.hoteldetails

import com.viniciusmello.fragments.repository.dao.IHotelRepository

class HotelDetailsPresenter (

    private val hotelDetailsView: HotelDetailsView,
    private val hotelRepository: IHotelRepository

        ) {

    fun loadHotel(id:Long) {
        hotelRepository.findHotelById(id) { hotel ->
            if (hotel != null)
                hotelDetailsView.showHotel(hotel)
            else
                hotelDetailsView.showErrorHotelNotFound()
        }
    }

}