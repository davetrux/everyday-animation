package com.truxall.everydayanimation.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist

internal class ArtistListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder>() {

    private val mInflater: LayoutInflater
    private var mArtists: List<Artist>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val itemView = mInflater.inflate(R.layout.connected_artist_list, parent, false)
        return ArtistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        if (mArtists != null) {
            val current = mArtists!![position]
            holder.artistNameView.text = current.name
            val bitmap =  BitmapFactory.decodeByteArray(current.thumbNail, 0, current.thumbNail.size)
            holder.arrtistThumbnail.setImageBitmap(bitmap)
        } else {
            // Covers the case of data not being ready yet.
            holder.artistNameView.text = "No Artists"
        }
    }

    internal fun setArtists(artists: List<Artist>?) {
        mArtists = artists
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mArtists != null)
            mArtists!!.size
        else
            0
    }

    internal inner class ArtistViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistNameView: TextView
        val arrtistThumbnail: ImageView

        init {
            artistNameView = itemView.findViewById(R.id.artist_name)
            arrtistThumbnail = itemView.findViewById(R.id.artist_image)
        }
    }
}