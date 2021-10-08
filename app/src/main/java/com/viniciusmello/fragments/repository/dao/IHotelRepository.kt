package com.viniciusmello.fragments.repository.dao

import com.viniciusmello.fragments.entities.Hotel

interface IHotelRepository {

    fun save(hotel: Hotel)
    fun remove(vararg  hotel: Hotel)
    fun findHotelById(id: Long, callback: (Hotel?) -> Unit)
    fun search(term: String, callback: (List<Hotel>) -> Unit)

}