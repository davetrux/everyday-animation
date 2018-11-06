package com.truxall.everydayanimation.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "artist")
@Parcelize
data class Artist(@ColumnInfo(name="name") val name: String,
                  @ColumnInfo(name="id") @PrimaryKey(autoGenerate = false) val id: Int,
                  @ColumnInfo(name="bio") val biography: String,
                  @ColumnInfo(name="genre") val genre: String,
                  @ColumnInfo(name="image", typeAffinity = ColumnInfo.BLOB) val image: ByteArray,
                  @ColumnInfo(name="thumbnail", typeAffinity = ColumnInfo.BLOB) val thumbNail: ByteArray): Parcelable {
}