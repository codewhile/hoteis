package com.viniciusmello.fragments.repository.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.viniciusmello.fragments.*
import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.repository.HotelSqlHelper

class HotelDao(ctx: Context) :
    IHotelRepository {

    private val hotelSqlHelper =
        HotelSqlHelper(ctx)


    fun useInformationContent(hotel: Hotel): ContentValues =
        ContentValues().apply {
            if (hotel.id != 0L) put(COLUMN_ID, hotel.id)
            put(COLUMN_NAME, hotel.nome)
            put(COLUMN_ADRESS, hotel.adress)
            put(COLUMN_RATING, hotel.rating)
        }


    private fun insert(hotel: Hotel) {
        val db = hotelSqlHelper.writableDatabase
        val content = useInformationContent(hotel)
        val id = db.insert(TABLE_HOTEL, null, content)
        if (id != -1L) hotel.id = id
        db.close()
    }

    private fun update(hotel: Hotel) {
        val db = hotelSqlHelper.writableDatabase
        val content = useInformationContent(hotel)
        val id =
            db.insertWithOnConflict(TABLE_HOTEL, null, content, SQLiteDatabase.CONFLICT_REPLACE)
        if (id != -1L) hotel.id = id
        db.close()
    }

    override fun save(hotel: Hotel) {
        if (hotel.id >= 0L)
            update(hotel)
        else
            insert(hotel)

    }

    override fun remove(vararg hotéis: Hotel) {


        val db = hotelSqlHelper.writableDatabase

        for (hotel in hotéis) {
            db.delete(TABLE_HOTEL, "$COLUMN_ID = ?", arrayOf(hotel.id.toString()))
        }

        db.close()

    }

    override fun findHotelById(id: Long, callback: (Hotel?) -> Unit) {

        val db = hotelSqlHelper.readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_HOTEL WHERE $COLUMN_ID = ?",
            arrayOf(id.toString())
        )

        Log.d("NGVL", "Id hotel = $id")

        val result = cursor.moveToNext()
        if (result) {
            Log.d("NGVL", "Cursor Verdadeiro")
        }
        else {
            Log.d("NGVL", "Cursor Falso")
        }
        val hotel = getHotelByCursor(cursor)

        cursor.close()

        db.close()

        callback(hotel)

    }

    private fun getHotelByCursor(cursor: Cursor) = Hotel().apply {
        id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        nome = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        adress = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESS))
        rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING))
    }

    override fun search(term: String, callback: (List<Hotel>) -> Unit) {
        val db = hotelSqlHelper.readableDatabase

        var query: String
        if (!term.isEmpty()) {
            query = "SELECT * FROM $TABLE_HOTEL WHERE $COLUMN_NAME LIKE ? ORDER BY $COLUMN_NAME"
        } else {
            query = "SELECT * FROM $TABLE_HOTEL ORDER BY $COLUMN_NAME"
        }

        val cursor = db.rawQuery(query, if (term.isEmpty()) null else arrayOf("%$term%"))

        val arrayHotel = ArrayList<Hotel>()

        while (cursor.moveToNext()) {
            arrayHotel.add(getHotelByCursor(cursor))
        }

        cursor.close()

        db.close()

        callback(arrayHotel)

    }

}