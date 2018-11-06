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
import timber.log.Timber

internal class ConnectedListAdapter internal constructor(context: Context, clickListener: ArtistClickListener) : RecyclerView.Adapter<ConnectedListAdapter.ArtistViewHolder>() {

    private val mInflater: LayoutInflater
    private var mArtists: List<Artist>? = null
    private var artistClickListener: ArtistClickListener

    init {
        this.mInflater = LayoutInflater.from(context)
        this.artistClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val itemView = mInflater.inflate(R.layout.connected_artist_list, parent, false)
        return ArtistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        if (mArtists != null) {
            val current = mArtists!![position]
            holder.artistNameView.text = current.name
            holder.artistNameView.transitionName = holder.artistNameView.transitionName + position
            holder.artistGenre.text = current.genre
            holder.artistGenre.transitionName = holder.artistGenre.transitionName + position
            val thumb =  BitmapFactory.decodeByteArray(current.thumbNail, 0, current.thumbNail.size)
            holder.artistThumbnail.setImageBitmap(thumb)
            holder.artistThumbnail.setOnClickListener {
                Timber.d("crap")
                this.artistClickListener.onArtistItemClick(holder.adapterPosition, current, holder.artistNameView, holder.artistGenre)
            }
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
        val artistThumbnail: ImageView
        val artistGenre: TextView

        init {
            artistNameView = itemView.findViewById(R.id.artist_name)
            artistGenre = itemView.findViewById(R.id.artist_genre)
            artistThumbnail = itemView.findViewById(R.id.artist_thumb)
        }
    }
}