package com.truxall.everydayanimation.ui

import android.widget.TextView
import com.truxall.everydayanimation.data.Artist

interface ArtistClickListener {
    fun onArtistItemClick(pos: Int, artist: Artist, sharedNameView: TextView, sharedGenreView: TextView)
}