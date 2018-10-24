package com.truxall.everydayanimation

import android.app.Application
import androidx.room.Room
import com.truxall.everydayanimation.data.ArtistDatabase
import timber.log.Timber

import java.lang.Exception


class ArtistApp: Application() {

    companion object {
        var database: ArtistDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        try {
            Utils.copyDataBase(this, Utils.dbName)
        }
        catch(ex: Exception) {
             Timber.e(ex, "Could not copy database file")
        }
        ArtistApp.database = Room.databaseBuilder(this, ArtistDatabase::class.java, Utils.dbName).build()
    }
}