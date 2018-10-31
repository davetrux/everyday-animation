package com.truxall.everydayanimation.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist
import timber.log.Timber

internal class ArtistListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder>() {

    private val mInflater: LayoutInflater
    private var mArtists: List<Artist>? = null
    var onItemClick: (() -> Unit)? = null

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
            holder.artistGenre.text = current.genre
            //holder.artistBio.text = current.biography
            val thumb =  BitmapFactory.decodeByteArray(current.thumbNail, 0, current.thumbNail.size)
            holder.artistThumbnail.setImageBitmap(thumb)
            val image =  BitmapFactory.decodeByteArray(current.image, 0, current.image.size)
            holder.artistImage.setImageBitmap(image)
        } else {
            // Covers the case of data not being ready yet.
            holder.artistNameView.text = "No Artists"
        }
//        val motionView = holder.itemView as MotionLayout
//        motionView.setOnClickListener {
//            motionView.transitionToEnd()
//            Timber.d("Tapped")
//        }
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
        val artistImage: ImageView
        val artistGenre: TextView
        //val artistBio: TextView

        init {
            artistNameView = itemView.findViewById(R.id.artist_name)
            artistGenre = itemView.findViewById(R.id.artist_genre)
            artistThumbnail = itemView.findViewById(R.id.artist_thumb)
            artistImage = itemView.findViewById(R.id.artist_image)
            // artistBio = itemView.findViewById(R.id.artist_bio)
//            itemView.setOnClickListener {
//                val motionView = itemView as MotionLayout
//                motionView.transitionToEnd()
//                Timber.d("Tapped")
//                //Toast.makeText(itemView.context, "tapped", Toast.LENGTH_SHORT).show()
//            }
        }
    }
}