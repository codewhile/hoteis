package com.viniciusmello.fragments.hotelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.ListFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.viniciusmello.fragments.entities.Hotel
import com.viniciusmello.fragments.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HotelListFragment :
    ListFragment(),
    HotelListView,
    ActionMode.Callback,
    AdapterView.OnItemLongClickListener {

    private val presenter: HotelListPresenter by inject {
        parametersOf(this)
    }


    private var actionMode:
            ActionMode? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        presenter.init()
        listView.onItemLongClickListener = this
    }

    override fun showHotels(hotels: List<Hotel>) {

        listAdapter = HotelAdapter(requireContext(),
            hotels)

            //ArrayAdapter<Hotel>(requireContext(),
              //  android.R.layout.simple_list_item_activated_1, hotels)

        listView.onItemLongClickListener = this

    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val hotel: Hotel = l.getItemAtPosition(position) as Hotel
        presenter.selectHotel(hotel)

    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        val hotel = listView.getItemAtPosition(position) as Hotel
        presenter.showDeleteMode()
        presenter.selectHotel(hotel)
        return true
    }

    override fun showHotelsDetails(hotel: Hotel) {

        val activity = activity

        if (activity is HotelClickListener)
            activity.onHotelClickListener(hotel)

    }

    fun searchHotel(term: String) {
        presenter.search(term)
    }

    fun clearSearch() {
        presenter.search("")
    }

    interface HotelClickListener {
        fun onHotelClickListener(hotel: Hotel)
    }

    override fun hideDeleteMode() {

        listView.onItemLongClickListener = this

        for (i in 0 until listView.count) {
            listView.setItemChecked(i, false)
        }

        listView.post {
            actionMode?.finish()
            listView.choiceMode =
                ListView.CHOICE_MODE_NONE
        }

    }

    override fun showDeleteMode() {

        val activity = activity as AppCompatActivity
        actionMode = activity.startSupportActionMode(this)
        listView.onItemLongClickListener = null
        listView.choiceMode =
            ListView.CHOICE_MODE_MULTIPLE

    }

    override fun showSelectedHotels(hotels: MutableList<Hotel>) {

        listView.post {

            for (i in 0 until listView.count) {

                val hotel = listView.getItemAtPosition(i) as Hotel

                if (hotels.contains(hotel)) {
                    listView.setItemChecked(i, true)
                }

            }

        }

    }

    override fun showCounterSelectedHotels(count: Int) {
        this.actionMode?.title = count.toString()
    }

    override fun showSnackBarUndoDelete() {


        Snackbar.make(listView, "Desfazer ação", Snackbar.LENGTH_SHORT).setAction("Desfazer") {

            presenter.undoDeleteItens()

        }.addCallback(object  : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {

                presenter.deleteTemp()

            }
        }).show()

    }


    interface HotelsDeletedListener {
        fun onHotelsDeleted(it: List<Hotel>)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.delete_list_itens_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean
            = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.menuDelete) {
            presenter.deleteSelected {
                if (activity is HotelsDeletedListener)
                    (activity as HotelsDeletedListener).onHotelsDeleted(it)
            }
            true
        } else false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        presenter.exitDeleteMode()
    }

}



