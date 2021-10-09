package com.viniciusmello.fragments.repository.dao

import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.repository.DBRoom

class HotelRepository(database: DBRoom) :
    IHotelRepository {

    private val hotelDao: HotelDao = database.hotelDao()

    override fun save(hotel: Hotel) {

        if (hotel.id == 0L) {
            val id: Long = hotelDao.save(hotel)
            hotel.id = id
        } else {
            hotelDao.update(hotel)
        }

    }

    override fun remove(vararg hotel: Hotel) {
        hotelDao.remove(*hotel)
    }

    override fun findHotelById(id: Long, callback: (Hotel?) -> Unit) {
        val hotel = hotelDao.findHotelById(id)
        callback(hotel)
    }

    override fun search(term: String, callback: (List<Hotel>) -> Unit) {

        callback(
            if (term.isEmpty())
                hotelDao.allHotels()
            else
                hotelDao.search(term)
        )

    }
}