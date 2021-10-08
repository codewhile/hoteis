package com.viniciusmello.fragments.commom

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.viniciusmello.fragments.*
import com.viniciusmello.fragments.hotelform.HotelFormFragment
import com.viniciusmello.fragments.hoteldetails.HotelDetailsActivity
import com.viniciusmello.fragments.hoteldetails.HotelDetailsFragment
import com.viniciusmello.fragments.hotelist.HotelListFragment
import com.viniciusmello.fragments.entities.Hotel

class HotelActivity :

    AppCompatActivity(),
    HotelListFragment.HotelClickListener,
    HotelListFragment.HotelsDeletedListener,
    MenuItem.OnActionExpandListener,
    HotelFormFragment.SaveHotelFormListener,
    SearchView.OnQueryTextListener {


    private lateinit var searchView: SearchView

    private var lastSearchTerm = ""
    private var hotelId:Long = 0L

    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hotel)

        if (savedInstanceState != null) {
            lastSearchTerm = savedInstanceState.getString(TERM, "")
            hotelId = savedInstanceState.getLong(EXTRA_LAST_HOTEL_SELECTED)
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            listFragment.hideDeleteMode()
            HotelFormFragment.newInstance().open(supportFragmentManager)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TERM, lastSearchTerm)
        outState.putLong(EXTRA_LAST_HOTEL_SELECTED, hotelId)
    }

    override fun onHotelClickListener(hotel: Hotel) {

        if (isTablet()) {
            showHotelDetailsFragment(hotel.id)
        } else {
            showHotelDetailsActivity(hotel.id)
        }
    }

    private fun showHotelDetailsActivity(id: Long) {
        HotelDetailsActivity.open(this, id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            listFragment.searchHotel(lastSearchTerm)
        }

    }


    private fun showHotelDetailsFragment(hotelId: Long) {

        this.hotelId = hotelId
        this.searchView.setOnQueryTextListener(null)
        val hotelDetailsFragment =
            HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, hotelDetailsFragment, HotelDetailsFragment.TAG)
            .commit()

    }

    private fun isTablet(): Boolean =
        findViewById<FrameLayout>(R.id.details) != null


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.hotel, menu)

        val menuItem = menu?.findItem(R.id.search)

        menuItem?.let {
            it.setOnActionExpandListener(this)
            searchView = it.actionView as SearchView
            searchView.queryHint = "Digite algo... "
            searchView.setOnQueryTextListener(this)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.information -> {
                AboutDialogFragment().show(supportFragmentManager, "about")
            }

            R.id.add -> {
                HotelFormFragment.newInstance()
                    .open(supportFragmentManager)
            }

        }

        return true

    }


    override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        this.lastSearchTerm = ""
        this.listFragment.clearSearch()
        return true

    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?):
            Boolean {

        lastSearchTerm = newText ?: ""
        this.listFragment.searchHotel(lastSearchTerm)
        return true

    }

    companion object {
        const val EXTRA_LAST_HOTEL_SELECTED = "hotel_selected"
        const val TERM ="term"
    }

    override fun onSaveHotelForm(hotel: Hotel) {
        Toast.makeText(this, "salvou", Toast.LENGTH_SHORT).show()
        listFragment.searchHotel(lastSearchTerm)
        val fragment = supportFragmentManager.findFragmentByTag(HotelDetailsFragment.TAG)

        if (fragment != null
            && hotel.id == hotelId) {
            showHotelDetailsFragment(hotelId)
        }


    }


    override fun onHotelsDeleted(hotels: List<Hotel>) {

        var hotelDetailsFragment: HotelDetailsFragment? =
            supportFragmentManager.findFragmentByTag(HotelDetailsFragment.TAG) as HotelDetailsFragment?

        if (hotelDetailsFragment != null) {

            val isLocal =
                hotels.find { it.id == hotelId} != null

            if (isLocal)
                supportFragmentManager
                    .beginTransaction()
                    .remove(hotelDetailsFragment)
                    .commit()

        }

    }

}