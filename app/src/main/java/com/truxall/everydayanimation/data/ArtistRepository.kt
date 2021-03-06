package com.truxall.everydayanimation.data

import android.app.Application
import androidx.lifecycle.LiveData


class ArtistRepository internal constructor(application: Application) {

    private val artistDao: ArtistDao

    init {
        val db = ArtistDatabase.getDatabase(application)
        artistDao = db!!.artistDao()
    }

    fun allArtists(): LiveData<List<Artist>> {
        val all = artistDao.getArtists()
        return all
    }

    fun albumsById(artistId: Int): LiveData<List<Album>> {
        val albums = artistDao.getAlbums(artistId)
        return albums
    }

    fun artistById(artistId: Int): LiveData<Artist> {
        return artistDao.getArtistById(artistId)
    }
}