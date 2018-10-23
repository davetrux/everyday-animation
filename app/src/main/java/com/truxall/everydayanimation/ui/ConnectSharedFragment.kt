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
import com.truxall.everydayanimation.data.Artist

class ConnectSharedFragment : Fragment() {

    private lateinit var viewModel: ConnectSharedViewModel
    private var containingView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_shared, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConnectSharedViewModel::class.java)

        val recyclerView =  view.findViewById<RecyclerView>(R.id.artist_view)
        val adapter = ArtistListAdapter(this.requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val albumObserver = Observer<List<Artist>?> { newList ->
            adapter.setArtists(newList)
        }
        viewModel.artistList.observe(this, albumObserver)
    }


    companion object {
        fun newInstance() = ConnectSharedFragment()
    }
}
