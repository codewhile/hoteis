package com.viniciusmello.fragments.hoteldetails

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.R
import com.viniciusmello.fragments.hotelform.HotelFormFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HotelDetailsFragment :
    Fragment(), HotelDetailsView {

    private val presenter:HotelDetailsPresenter by inject {
        parametersOf(this)
    }


    private val hotelId: Long? by lazy {
        this.arguments?.getLong(HOTEL_ID) ?: 0
    }

    private var hotel: Hotel? = null


    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotelId?.let {
            presenter.loadHotel(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.hotel_details_share, menu)

        val shareItem = menu.findItem(R.id.shareItem)

        shareActionProvider =
            MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider?

        setShareActionProviderOptions()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.editar -> {
                HotelFormFragment.newInstance(this.arguments?.getLong(HOTEL_ID) ?: 0).open(parentFragmentManager)
            }
        }

        return true
    }

    private fun setShareActionProviderOptions() {

        shareActionProvider?.setShareIntent(Intent(Intent.ACTION_SEND).apply {

            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(
                R.string.hotel_share,
                hotel?.nome,
                hotel?.rating))

        })

    }

    override fun showHotel(hotel: Hotel) {

        this.hotel = hotel

        view?.let {
            it.findViewById<TextView>(R.id.textView).text = hotel.nome
            it.findViewById<TextView>(R.id.textView2).text = hotel.adress
            it.findViewById<RatingBar>(R.id.ratingBar).rating = hotel.rating
        }

    }

    override fun showErrorHotelNotFound() {

        view?.let {
            it.findViewById<TextView>(R.id.textView).text = getString(R.string.error)
            it.findViewById<TextView>(R.id.textView2).visibility = View.GONE
            it.findViewById<TextView>(R.id.ratingBar).visibility = View.GONE
        }

    }


    companion object {

        const val TAG = "hotel_tag"
        private const val HOTEL_ID = "hotel_id"

        fun newInstance(id: Long): HotelDetailsFragment =

            HotelDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(HOTEL_ID, id)
                }

            }


    }


}