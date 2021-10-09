package com.viniciusmello.fragments.repository.dao

import androidx.room.*
import com.viniciusmello.fragments.COLUMN_ID
import com.viniciusmello.fragments.COLUMN_NAME
import com.viniciusmello.fragments.TABLE_HOTEL
import com.viniciusmello.fragments.entities.Hotel

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(hotel: Hotel):Long

    @Update
    fun update(hotel:Hotel)

    @Delete
    fun remove(vararg hotel: Hotel):Int

    @Query("SELECT * FROM $TABLE_HOTEL")
    fun allHotels(): List<Hotel>

    @Query("SELECT * FROM $TABLE_HOTEL WHERE $COLUMN_ID = :id ORDER BY $COLUMN_NAME")
    fun findHotelById(id: Long): Hotel

    @Query("SELECT * FROM $TABLE_HOTEL WHERE $COLUMN_NAME = :term ORDER BY $COLUMN_NAME")
    fun search(term: String): List<Hotel>

}