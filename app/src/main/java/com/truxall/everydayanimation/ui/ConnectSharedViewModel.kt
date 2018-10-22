package com.truxall.everydayanimation.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
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