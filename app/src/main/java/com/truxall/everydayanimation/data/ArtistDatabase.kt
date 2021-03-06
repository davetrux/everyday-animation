package com.truxall.everydayanimation.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.truxall.everydayanimation.Utils

@Database(entities = [Artist::class, Album::class], version = 1)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao

    companion object {
        private var INSTANCE: ArtistDatabase? = null

        internal fun getDatabase(context: Context): ArtistDatabase? {
            if (INSTANCE == null) {
                synchronized(ArtistDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ArtistDatabase::class.java, Utils.dbName)
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}