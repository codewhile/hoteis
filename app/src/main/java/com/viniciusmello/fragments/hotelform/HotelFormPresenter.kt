package com.viniciusmello.fragments.hotelform

import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.repository.dao.IHotelRepository

class HotelFormPresenter(

    private val hotelFormView: HotelFormView,
    private val hotelRepository: IHotelRepository
) {

    fun loadById(id: Long) {

        hotelRepository.findHotelById(id) {

            it?.let {
                hotelFormView.showHotel(it)
            }
        }

    }

    fun saveHotel(hotel: Hotel): Boolean {

        if (HotelFormValidator.validate(hotel))

            try {

                hotelRepository.save(hotel)
                return true

            } catch (e: Exception) {

                hotelFormView.showErrorSaveHotel()
            }
        else {

            hotelFormView.showErrorValidateHotel()

        }

        return false

    }

}