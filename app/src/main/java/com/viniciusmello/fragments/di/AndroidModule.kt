package com.viniciusmello.fragments.di

import com.viniciusmello.fragments.repository.dao.HotelDao
import com.viniciusmello.fragments.hoteldetails.HotelDetailsPresenter
import com.viniciusmello.fragments.hoteldetails.HotelDetailsView
import com.viniciusmello.fragments.hotelform.HotelFormPresenter
import com.viniciusmello.fragments.hotelform.HotelFormView
import com.viniciusmello.fragments.hotelist.HotelListPresenter
import com.viniciusmello.fragments.hotelist.HotelListView
import com.viniciusmello.fragments.repository.DBRoom
import com.viniciusmello.fragments.repository.dao.HotelRepository
import com.viniciusmello.fragments.repository.dao.IHotelRepository
import org.koin.dsl.module

val androidModule = module {

    single {
        this
    }

    single<IHotelRepository> {
        HotelRepository(DBRoom.getDatabase(context = get()))
    }

    factory<HotelListPresenter> { (view : HotelListView) ->
        HotelListPresenter(view, get())
    }

    factory<HotelDetailsPresenter> { (view: HotelDetailsView) ->

        HotelDetailsPresenter(view, get())

    }

    factory<HotelFormPresenter> { (view: HotelFormView) ->

        HotelFormPresenter(view, get())

    }

}
