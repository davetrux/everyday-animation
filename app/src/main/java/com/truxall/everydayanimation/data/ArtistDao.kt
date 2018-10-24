package com.truxall.everydayanimation.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun getArtists(): LiveData<List<Artist>>

    @Query("SELECT * FROM artist WHERE id = :artistId")
    fun getArtistById(artistId: Int): LiveData<Artist>

    @Query("SELECT * FROM album WHERE artistId = :artistId ORDER BY year ASC")
    fun getAlbums(artistId: Int): LiveData<List<Album>>
}