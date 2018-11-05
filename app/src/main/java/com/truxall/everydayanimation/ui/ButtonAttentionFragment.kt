package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.truxall.everydayanimation.R
import android.view.animation.AnimationUtils


class ButtonAttentionFragment: Fragment() {

    private var shakeRepeats: Int = 0
    private val totalShakes: Int = 2

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

        shake.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
               if (shakeRepeats < totalShakes) {
                   handler.postDelayed(runnable, 1500)
                   shakeRepeats++
               }
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        handler.postDelayed(runnable, 1500)
    }
}