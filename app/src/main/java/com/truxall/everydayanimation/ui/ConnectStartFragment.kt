package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private val TAG: String = ConnectStartFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.connected_start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConnectSharedViewModel::class.java)

        val recyclerView =  view.findViewById<RecyclerView>(R.id.artist_recycler_view)
        val adapter = ConnectedListAdapter(this.requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val albumObserver = Observer<List<Artist>?> { newList ->
            adapter.setArtists(newList)
        }
        viewModel.artistList.observe(this, albumObserver)
    }

    override fun onArtistItemClick(pos: Int, artist: Artist, sharedNameView: TextView, sharedGenreView: TextView) {
        // The transition set is needed to apply both ChangeTransform and ChangeBounds.
        //  ChangeTransform is needed because the animated views are nested in a recyclerview
        val transitionSet = TransitionSet()
        transitionSet.addTransition(ChangeTransform())
        transitionSet.addTransition(ChangeBounds())
        transitionSet.duration = 500

        // We could add a Path to the transition set to control how the items move

        // Get the transition names from the view XML
        val titleName = ViewCompat.getTransitionName(sharedNameView)
        val genreName = ViewCompat.getTransitionName(sharedGenreView)
        // Pass them to the new fragment so it knows which items to animate
        val detailFragment = ConnectedEndFragment.newInstance(artist, titleName!!, genreName!!)

        detailFragment.sharedElementEnterTransition = transitionSet
        detailFragment.enterTransition = Fade()
        exitTransition = Fade()
        detailFragment.sharedElementReturnTransition = transitionSet

        fragmentManager!!
                .beginTransaction()
                .addSharedElement(sharedNameView, titleName)
                .addSharedElement(sharedGenreView, genreName)
                .addToBackStack(TAG)
                .replace(R.id.nav_host, detailFragment)
                .commit()
    }


    companion object {
        fun newInstance() = ConnectStartFragment()
    }
}
