package com.viniciusmello.fragments.hotelform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.viniciusmello.fragments.R
import com.viniciusmello.fragments.entities.Hotel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HotelFormFragment :
    DialogFragment(), HotelFormView {

    private val hotelId: Long by lazy {
        arguments?.getLong(ID, 0) ?: 0
    }

    private val presenter: HotelFormPresenter by inject {
        parametersOf(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hotel_form, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (hotelId != 0L)
            presenter.loadById(hotelId)

        val edtAdress: EditText =
            view.findViewById(R.id.edtDois)

        edtAdress.setOnEditorActionListener { _, i, _ ->
            handleEvent(i)
        }

        dialog?.setTitle("Adicionando novo hotel")
    }

    fun handleEvent(i: Int): Boolean {

        if (EditorInfo.IME_ACTION_DONE == i) {

            val hotel: Hotel? = salvarHotel()

            if (activity is SaveHotelFormListener &&
                hotel != null
            )

                (activity as SaveHotelFormListener)
                    .onSaveHotelForm(hotel)


            this.dialog?.dismiss()

            return true

        }

        return false

    }

    fun salvarHotel(): Hotel? {

        val hotel = Hotel()
        hotel.id = hotelId
        hotel.nome = view?.findViewById<EditText>(R.id.edtUm)?.text.toString()
        hotel.adress = view?.findViewById<EditText>(R.id.edtDois)?.text.toString()
        hotel.rating = view?.findViewById<RatingBar>(R.id.rating)?.rating ?: 0f

        return if (presenter.saveHotel(hotel))
            hotel
        else
            null

    }

    override fun showHotel(hotel: Hotel) {
        view?.let {
            it.findViewById<EditText>(R.id.edtUm).setText(hotel.nome)
            it.findViewById<EditText>(R.id.edtDois).setText(hotel.adress)
            it.findViewById<RatingBar>(R.id.rating).rating = hotel.rating
        }
    }

    override fun showErrorSaveHotel() {
        Toast.makeText(
            requireContext(),
            "Error ao salvar",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showErrorValidateHotel() {
        Toast.makeText(
            requireContext(),
            "Error ao salvar",
            Toast.LENGTH_SHORT
        ).show()
    }

    interface SaveHotelFormListener {
        fun onSaveHotelForm(hotel: Hotel)
    }

    fun open(fm: FragmentManager) {

        if (fm.findFragmentByTag(TAG) == null)
            show(fm, TAG)

    }

    companion object {

        private const val ID = "hotel_form_id_long"
        const val TAG = "hotel_form_fragment_tag"

        fun newInstance(hotelId: Long = 0) =
            HotelFormFragment()
                .apply {
                    arguments = Bundle().apply {
                        this.putLong(ID, hotelId)
                    }
                }

    }

}