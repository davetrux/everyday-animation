package com.truxall.everydayanimation.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(@ColumnInfo(name="name") val name: String,
                  @ColumnInfo(name="id") @PrimaryKey(autoGenerate = false) val id: Int,
                  @ColumnInfo(name="bio") val biography: String,
                  @ColumnInfo(name="genre") val genre: String,
                  @ColumnInfo(name="image", typeAffinity = ColumnInfo.BLOB) val image: ByteArray,
                  @ColumnInfo(name="thumbnail", typeAffinity = ColumnInfo.BLOB) val thumbNail: ByteArray) {
}