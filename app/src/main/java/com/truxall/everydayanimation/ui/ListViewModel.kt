package com.truxall.everydayanimation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.truxall.everydayanimation.data.Album
import com.truxall.everydayanimation.data.ArtistRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: ArtistRepository

    internal val artistAlbums: LiveData<List<Album>>

    init {
        mRepository = ArtistRepository(application)
        artistAlbums = mRepository.albumsById(111247)
    }
}
