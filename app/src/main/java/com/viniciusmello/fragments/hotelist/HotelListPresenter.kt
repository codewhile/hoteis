package com.viniciusmello.fragments.hotelist

import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.repository.dao.IHotelRepository

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: IHotelRepository
) {

    private var lastTerm = ""
    private var deleteMode: Boolean= false
    private val itens = mutableListOf<Hotel>()
    private var listDeleted = mutableListOf<Hotel>()

    fun search(term:String) {

        this.lastTerm = term

        this.repository.search(term) { hotels ->
            view.showHotels(hotels)
        }
    }

    fun showDetails(hotel: Hotel) {
        view.showHotelsDetails(hotel)
    }

    fun selectHotel(hotel: Hotel) {

        if (this.deleteMode) {

            toggleHotelSelectionMode(hotel)

            if (itens.size == 0) {
                view.hideDeleteMode()
            }
            else {
                view.showCounterSelectedHotels(itens.size)
                view.showSelectedHotels(itens)
            }

        }
        else {
            view.showHotelsDetails(hotel)
        }
    }

    private fun toggleHotelSelectionMode(hotel: Hotel) {

        val hotelSearched: Hotel? = itens.find {
            it.id == hotel.id
        }

        if (hotelSearched == null) {

            itens.add(hotel)

        }
        else {

            itens.removeAll {
                it.id == hotel.id
            }

        }

    }

    fun refresh() {
        search("")
    }

    fun showDeleteMode() {
        this.deleteMode = true
        view.showDeleteMode()
    }

    fun exitDeleteMode() {
        deleteMode = false
        itens.clear()
        view.hideDeleteMode()
    }

    fun init() {

        if (deleteMode) {
            view.showDeleteMode()
            view.showCounterSelectedHotels(itens.size)
            view.showSelectedHotels(itens)
        }
        else {
            refresh()
        }

    }

    fun deleteSelected(callback: (List<Hotel>) -> Unit) {

        listDeleted.addAll(itens)
        repository.remove(*itens.toTypedArray())
        refresh()
        view.hideDeleteMode()
        callback(itens)
        view.showSnackBarUndoDelete()

    }

    fun undoDeleteItens() {

        listDeleted.forEach {
            repository.save(it)
        }

        listDeleted.clear()

        search(lastTerm)

    }

    fun deleteTemp() {
        listDeleted.clear()
    }
}
