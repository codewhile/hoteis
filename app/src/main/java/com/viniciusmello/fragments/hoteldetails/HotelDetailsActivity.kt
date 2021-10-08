package com.viniciusmello.fragments.hoteldetails

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.viniciusmello.fragments.R
import com.viniciusmello.fragments.hotelform.HotelFormFragment
import com.viniciusmello.fragments.entities.Hotel

class HotelDetailsActivity :
    AppCompatActivity(), HotelFormFragment.SaveHotelFormListener {

    private val id:Long by lazy {
        this.intent.getLongExtra(ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)
        Toast.makeText(this, id.toString(), Toast.LENGTH_LONG).show()
        if (savedInstanceState == null) {
            showHotelDetailsFragment()
        }

    }

    private fun showHotelDetailsFragment() {

        val fragment : HotelDetailsFragment =
            HotelDetailsFragment.newInstance(id)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.details, fragment, HotelDetailsFragment.TAG)
                .commit()

    }

    companion object {

        private const val ID = "hotel_id"

        fun open(act:Activity, id: Long) {
            act.startActivityForResult(Intent(act, HotelDetailsActivity::class.java).apply {
                putExtra(ID, id)
            }, 0)
        }
    }

    override fun onSaveHotelForm(hotel: Hotel) {

        setResult(RESULT_OK)
        showHotelDetailsFragment()

    }

}