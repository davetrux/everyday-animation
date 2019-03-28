package com.truxall.everydayanimation.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist


class ConnectedEndFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.connected_end_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the data passed in from the new instance created by the caller
        val artist = arguments!!.getParcelable<Artist>(ARTIST_ITEM)
        val nameTransition = arguments!!.getString(NAME_TRANS)
        val genreTransition = arguments!!.getString(GENRE_TRANS)


        val nameView = view.findViewById<TextView>(R.id.artist_name)
        nameView.transitionName = nameTransition
        nameView.text = artist!!.name

        val bioView = view.findViewById<TextView>(R.id.artist_bio)
        bioView.text = artist.biography

        val genreView = view.findViewById<TextView>(R.id.artist_genre)
        genreView.text = artist.genre
        genreView.transitionName = genreTransition

        val imageView = view.findViewById<ImageView>(R.id.artist_image)
        val bitmap =  BitmapFactory.decodeByteArray(artist.image, 0, artist.image.size)
        imageView.setImageBitmap(bitmap)
    }

    companion object {
        private const val ARTIST_ITEM = "artist"
        private const val GENRE_TRANS = "artist_name"
        private const val NAME_TRANS = "artist_genre"
        fun newInstance(artist: Artist, nameTransition: String, genreTransition: String): ConnectedEndFragment {
            val fragment = ConnectedEndFragment()
            val bundle = Bundle()
            bundle.putParcelable(this.ARTIST_ITEM, artist)
            bundle.putString(GENRE_TRANS, genreTransition)
            bundle.putString(NAME_TRANS, nameTransition)
            fragment.arguments = bundle
            return fragment
        }
    }
}