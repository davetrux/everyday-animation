package com.truxall.everydayanimation.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Album

internal class AlbumListAdapter internal constructor(context: Context) : RecyclerView.Adapter<AlbumListAdapter.WordViewHolder>() {

    private val mInflater: LayoutInflater
    private var mAlbums: List<Album>? = null // Cached copy of words

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.album_list, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (mAlbums != null) {
            val current = mAlbums!![position]
            holder.wordItemView.text = current.name
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.text = "No Album"
        }
    }

    internal fun setWords(words: List<Album>?) {
        mAlbums = words
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mAlbums != null)
            mAlbums!!.size
        else
            0
    }

    internal inner class WordViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView

        init {
            wordItemView = itemView.findViewById(R.id.textView)
        }
    }
}