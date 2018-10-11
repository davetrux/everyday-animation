package com.truxall.everydayanimation.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(Artist::class), (Album::class)], version = 1)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}