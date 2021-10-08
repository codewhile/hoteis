package com.viniciusmello.fragments.hotelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.viniciusmello.fragments.R
import com.viniciusmello.fragments.entities.Hotel

class HotelAdapter(val ctx: Context, val hotels: List<Hotel>) :
    ArrayAdapter<Hotel>(ctx, 0, hotels) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val holder: ViewHolder
        val view:View

        if (convertView == null) {

            view =
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_tela_listagem, parent, false)

            holder = ViewHolder(view)

            view.tag = holder

        }
        else {

            view = convertView

            holder = convertView.tag
                    as ViewHolder
        }


        val hotel = hotels[position]
        holder.nome.text = hotel.nome
        holder.hotelRating.rating = hotel.rating

        return view
    }

    class ViewHolder(view:View) {
        val nome = view.findViewById<TextView>(R.id.hotelName)
        val hotelRating = view.findViewById<RatingBar>(R.id.hotelRating)
    }


}