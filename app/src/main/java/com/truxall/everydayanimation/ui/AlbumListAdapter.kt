package com.truxall.everydayanimation.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Album
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.maxHeight
import android.R.attr.maxWidth



internal class AlbumListAdapter internal constructor(context: Context) : RecyclerView.Adapter<AlbumListAdapter.WordViewHolder>() {

    private val mInflater: LayoutInflater
    private var mAlbums: List<Album>? = null // Cached copy of words

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.album_list, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        if (mAlbums != null) {
            val current = mAlbums!![position]
            holder.wordItemView.text = current.name
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.text = "No Album"
        }
    }

    internal fun setWords(words: List<Album>?) {
        mAlbums = words
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mAlbums != null)
            mAlbums!!.size
        else
            0
    }

    internal inner class WordViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView

        init {
            wordItemView = itemView.findViewById(R.id.textView)
        }
    }

    // https://stackoverflow.com/questions/2577221/android-how-to-create-runtime-thumbnail
    private fun createThumbNail(image: ByteArray, width: Int, height: Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(image, 0, image.size, options)

        val wRatio_inv = options.outWidth.toFloat() / maxWidth
        val hRatio_inv = options.outHeight.toFloat() / maxHeight // Working with inverse ratios is more comfortable
        val finalW: Int
        val finalH: Int
        val minRatio_inv /* = max{Ratio_inv} */: Int

        if (wRatio_inv > hRatio_inv) {
            minRatio_inv = wRatio_inv.toInt()
            finalW = maxWidth
            finalH = Math.round(options.outHeight / wRatio_inv)
        } else {
            minRatio_inv = hRatio_inv.toInt()
            finalH = maxHeight
            finalW = Math.round(options.outWidth / hRatio_inv)
        }

        options.inSampleSize = this.pow2Ceil(minRatio_inv) // pow2Ceil: A utility function that comes later
        options.inJustDecodeBounds = false // Decode bitmap with inSampleSize set

        return Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(image, 0, image.size, options),
                finalW, finalH, true)
    }

    private fun pow2Ceil(number: Int): Int {
        return 1 shl -(Integer.numberOfLeadingZeros(number) + 1) // is equivalent to:
        // return Integer.rotateRight(1, Integer.numberOfLeadingZeros(number) + 1);
    }
}