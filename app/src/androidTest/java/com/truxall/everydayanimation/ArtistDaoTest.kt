package com.truxall.everydayanimation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.truxall.everydayanimation.data.ArtistDao
import com.truxall.everydayanimation.data.ArtistDatabase
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistDaoTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: ArtistDatabase
    private lateinit var artistDao: ArtistDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        try {
            Utils.copyDataBase(context, Utils.dbName)
            database = Room.databaseBuilder(context, ArtistDatabase::class.java, Utils.dbName).allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message)
        }
        artistDao = database.artistDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAllArtists() {

        val artistList = artistDao.getArtists()
        Assert.assertEquals(8, artistList.size)
    }

    @Test
    fun testAllAlbums() {

        val albumList = artistDao.getAlbuns(111247)
        Assert.assertEquals(12, albumList.size)
    }
}