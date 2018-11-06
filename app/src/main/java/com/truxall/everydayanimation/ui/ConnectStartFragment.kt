package com.truxall.everydayanimation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist
import androidx.core.view.ViewCompat
import androidx.transition.*


class ConnectStartFragment : Fragment(), ArtistClickListener {

    private lateinit var viewModel: ConnectSharedViewModel
    val TAG = ConnectStartFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.connected_start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConnectSharedViewModel::class.java)

        val recyclerView =  view.findViewById<RecyclerView>(R.id.artist_recycler_view)
        val adapter = ArtistListAdapter(this.requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val albumObserver = Observer<List<Artist>?> { newList ->
            adapter.setArtists(newList)
        }
        viewModel.artistList.observe(this, albumObserver)
    }

    override fun onArtistItemClick(pos: Int, artist: Artist, shareTitleViewView: TextView, sharedGenreView: TextView) {
        // Toast.makeText(this.context, "fragmenttapped", Toast.LENGTH_SHORT).show()
        val titleName = ViewCompat.getTransitionName(shareTitleViewView)
        val genreName = ViewCompat.getTransitionName(sharedGenreView)!!
        val detailFragment = ConnectedEndFragment.newInstance(artist, titleName!!, genreName)

        detailFragment.sharedElementEnterTransition = ChangeBounds()
        detailFragment.enterTransition = Fade()
        exitTransition = Fade()
        detailFragment.sharedElementReturnTransition = ChangeBounds()

        fragmentManager!!
                .beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(shareTitleViewView, titleName)
                //.addSharedElement(sharedGenreView, genreName)
                .addToBackStack(TAG)
                .replace(R.id.nav_host, detailFragment)
                .commit()
    }


    companion object {
        fun newInstance() = ConnectStartFragment()
    }
}
