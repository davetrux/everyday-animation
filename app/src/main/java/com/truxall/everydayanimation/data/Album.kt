package com.truxall.everydayanimation.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "album")
data class Album (@ColumnInfo(name="name") val name: String,
                  @ColumnInfo(name="id") @PrimaryKey(autoGenerate = false) val id: Int,
                  @ColumnInfo(name="artistId") val artistId: Int,
                  @ColumnInfo(name="year") val year: Int,
                  @ColumnInfo(name="thumbnail", typeAffinity = ColumnInfo.BLOB) val thumbNail: ByteArray) {
}