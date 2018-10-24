package com.truxall.everydayanimation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist

class ConnectSharedFragment : Fragment() {

    private lateinit var viewModel: ConnectSharedViewModel
    private var containingView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.connected_fragment, container, false)
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
