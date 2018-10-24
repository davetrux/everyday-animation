package com.truxall.everydayanimation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.truxall.everydayanimation.data.Artist
import com.truxall.everydayanimation.data.ArtistRepository

class ConnectSharedViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: ArtistRepository

    internal val artistList: LiveData<List<Artist>>

    init {
        mRepository = ArtistRepository(application)
        artistList = mRepository.allArtists()
    }
}