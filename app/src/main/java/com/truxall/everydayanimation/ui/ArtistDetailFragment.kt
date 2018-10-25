package com.truxall.everydayanimation.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.data.Artist

class ArtistDetailFragment : Fragment() {

    private lateinit var viewModel: ArtistDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel::class.java)

        val artistObserver = Observer<Artist> { artist ->
            val nameView = view.findViewById<TextView>(R.id.artist_name)
            nameView.text = artist.name
            val bioView = view.findViewById<TextView>(R.id.artist_bio)
            bioView.text = artist.biography
            val genreView = view.findViewById<TextView>(R.id.artist_genre)
            genreView.text = artist.genre
            val imageView = view.findViewById<ImageView>(R.id.artist_image)
            val bitmap =  BitmapFactory.decodeByteArray(artist.image, 0, artist.image.size)
            imageView.setImageBitmap(bitmap)
        }
        viewModel.artist.observe(this, artistObserver)
    }
        companion object {
        fun newInstance() = ArtistDetailFragment()
    }
}