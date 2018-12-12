package com.truxall.everydayanimation.ui

import android.animation.*
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.truxall.everydayanimation.R


class ButtonAttentionFragment: Fragment() {

    private var shakeRepeats: Int = 0
    private val totalShakes: Int = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.button_attention_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the view we want to animate
        val fab = view.findViewById<FloatingActionButton>(R.id.attention_fab)
        // Remember the starting position
        val x = fab.x

        // Create animations to move the button left to right and rotate a little
        val translate = ObjectAnimator.ofFloat(fab, "translationX", -5f, 5f)
        translate.repeatCount = 10
        translate.repeatMode = ValueAnimator.REVERSE

        val rotate = ObjectAnimator.ofFloat(fab, "rotation", -10f, 10f)
        rotate.repeatCount = 10
        rotate.repeatMode = ValueAnimator.REVERSE

        // Run the two animations together as a set
        val animSetXY = AnimatorSet()
        animSetXY.duration = 70
        animSetXY.playTogether(rotate, translate)

        val handler = Handler()
        val runnable = {
            animSetXY.start()
        }

        // When the rotation is done, reset the view, track the number of runs, and run again
        rotate.addListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator) {
                fab.x = x
                fab.rotation = 0f
                if (shakeRepeats < totalShakes) {
                    handler.postDelayed(runnable, 2000)
                    shakeRepeats++
                }
            }
        })

        // Start the handler
        handler.postDelayed(runnable, 2000)
    }
}