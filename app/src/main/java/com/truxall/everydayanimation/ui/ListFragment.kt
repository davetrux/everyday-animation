package com.truxall.everydayanimation.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Album

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private var containingView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.containingView = inflater.inflate(R.layout.list_fragment, container, false)
        return this.containingView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        val recyclerView =  this.containingView!!.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = AlbumListAdapter(this.requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val albumObserver = Observer<List<Album>?> { newList ->
            adapter.setWords(newList)
        }
        viewModel!!.artistAlbums.observe(this, albumObserver)
    }

}
