package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.truxall.everydayanimation.R
import android.view.animation.AnimationUtils


class ButtonAttentionFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.button_attention_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = view.findViewById<FloatingActionButton>(R.id.attention_fab)
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        val runnable = {
            fab.startAnimation(shake)
        }

        val handler = Handler()
        handler.postDelayed(runnable, 1500)
        handler.postDelayed(runnable, 3000)
        handler.postDelayed(runnable, 4500)
        handler.postDelayed(runnable, 6000)
    }
}