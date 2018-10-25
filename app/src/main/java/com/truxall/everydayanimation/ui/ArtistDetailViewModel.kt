package com.truxall.everydayanimation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.truxall.everydayanimation.data.Artist
import com.truxall.everydayanimation.data.ArtistRepository

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: ArtistRepository

    internal val artist: LiveData<Artist>

    init {
        mRepository = ArtistRepository(application)
        artist = mRepository.artistById(111464)
    }
}