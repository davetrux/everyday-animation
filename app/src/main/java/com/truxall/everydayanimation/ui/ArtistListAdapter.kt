package com.truxall.everydayanimation.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist

internal class ArtistListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder>() {

    private val mInflater: LayoutInflater
    private var mArtists: List<Artist>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val itemView = mInflater.inflate(R.layout.album_list, parent, false)
        return ArtistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        if (mArtists != null) {
            val current = mArtists!![position]
            holder.wordItemView.text = current.name
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.text = "No Artist"
        }
    }

    internal fun setArtists(artists: List<Artist>?) {
        mArtists = artists
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mArtists != null)
            mArtists!!.size
        else
            0
    }

    internal inner class ArtistViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView

        init {
            wordItemView = itemView.findViewById(R.id.textView)
        }
    }
}