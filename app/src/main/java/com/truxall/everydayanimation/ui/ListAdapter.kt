package com.truxall.everydayanimation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Album
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


internal class ListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ListAdapter.AlbumViewHolder>() {

    private val mInflater: LayoutInflater
    private var mAlbums: List<Album>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = mInflater.inflate(R.layout.list_albums, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        if (mAlbums != null) {
            val current = mAlbums!![position]
            holder.albumItemView.text = current.name
            val bitmap =  BitmapFactory.decodeByteArray(current.thumbNail, 0, current.thumbNail.size)
            holder.albumThumbView.setImageBitmap(bitmap)
        } else {
            // Covers the case of data not being ready yet.
            holder.albumItemView.text = "No Albums"
        }
    }

    internal fun setAlbums(albums: List<Album>?) {
        mAlbums = albums
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mAlbums != null)
            mAlbums!!.size
        else
            0
    }

    internal inner class AlbumViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumItemView: TextView
        val albumThumbView: ImageView

        init {
            albumItemView = itemView.findViewById(R.id.textView)
            albumThumbView = itemView.findViewById(R.id.imageView)
        }
    }
}