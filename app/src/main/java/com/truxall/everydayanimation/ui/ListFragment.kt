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
import android.view.animation.AnimationUtils

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Album
import com.truxall.everydayanimation.R.id.album_view
import android.view.animation.LayoutAnimationController





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

        val recyclerView =  this.containingView!!.findViewById<RecyclerView>(R.id.album_view)
        val adapter = AlbumListAdapter(this.requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        // Apply animation to the list
        val resId = R.anim.layout_animation_fall_down
        val animation = AnimationUtils.loadLayoutAnimation(this.context, resId)
        recyclerView.layoutAnimation = animation

        val albumObserver = Observer<List<Album>?> { newList ->
            adapter.setWords(newList)
            // Run the animation on update
            // this.runLayoutAnimation(recyclerView)
        }
        viewModel!!.artistAlbums.observe(this, albumObserver)
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

}
