package com.truxall.everydayanimation.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun getArtists(): LiveData<List<Artist>>

    @Query("SELECT * FROM artist WHERE id = :artistId")
    fun getArtistById(artistId: Int): Artist

    @Query("SELECT * FROM album WHERE artistId = :artistId ORDER BY year DESC")
    fun getAlbuns(artistId: Int): List<Album>
}