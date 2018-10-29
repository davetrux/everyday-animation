package com.truxall.everydayanimation.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.truxall.everydayanimation.R

class FloatingMenuFragment : Fragment() {

    companion object {
        fun newInstance() = FloatingMenuFragment()
    }

    private lateinit var viewModel: FloatingMenuViewModel
    private var menuOpen: Boolean = false
    private lateinit var fab: FloatingActionButton
    private lateinit var createButton: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var shareLayout: LinearLayout
    private lateinit var addLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.floating_menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FloatingMenuViewModel::class.java)

        this.fab = view.findViewById(R.id.mainFab)
        this.shareButton = view.findViewById(R.id.shareFab)
        this.createButton = view.findViewById(R.id.createFab)

        this.shareLayout = view.findViewById(R.id.shareLayout)
        this.addLayout = view.findViewById(R.id.createLayout)

        this.fab.setOnClickListener {
            val motionView = view as MotionLayout
            if(this.menuOpen) {
                motionView.transitionToStart()
                this.menuOpen = false
            } else {
                motionView.transitionToEnd()
                this.menuOpen = true
            }
        }

        this.shareButton.setOnClickListener {
            this.menuClicked(view)
        }
        this.createButton.setOnClickListener {
            this.menuClicked(view)
        }
    }

    private fun menuClicked(view: View) {
        Toast.makeText(view.context, "Tapped", Toast.LENGTH_SHORT).show()
    }
    private fun openMenu() {
        this.shareLayout.visibility = View.VISIBLE
        this.addLayout.visibility = View.VISIBLE
    }

    private fun closeMenu() {
        this.shareLayout.visibility = View.INVISIBLE
        this.addLayout.visibility = View.INVISIBLE
    }
}
