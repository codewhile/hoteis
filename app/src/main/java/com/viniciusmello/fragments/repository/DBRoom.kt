package com.viniciusmello.fragments.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viniciusmello.fragments.DATABASE_NAME
import com.viniciusmello.fragments.DATABASE_VERSION
import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.repository.dao.HotelDao

@Database(
    entities = [Hotel::class],
    version = DATABASE_VERSION
)
abstract class DBRoom() : RoomDatabase() {

    abstract fun hotelDao(): HotelDao


    companion object {

        private var instance: DBRoom? = null

        fun getDatabase(context: Context): DBRoom {

            if (instance == null) {

                instance = Room
                    .databaseBuilder(context, DBRoom::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }

            return instance as DBRoom

        }

        fun destroyInstance() {
            instance = null
        }

    }

}