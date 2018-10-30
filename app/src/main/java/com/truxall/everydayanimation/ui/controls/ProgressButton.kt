package com.truxall.everydayanimation.ui.controls

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import com.truxall.everydayanimation.R


class ProgressButton : AppCompatButton {

    private enum class State {
        PROGRESS, IDLE
    }

    private lateinit var mGradientDrawable: StateListDrawable
    private lateinit var mState: State
    private var mIsMorphingInProgress: Boolean = false
    private lateinit var mMorphingAnimatorSet: AnimatorSet
    private var mAnimatedDrawable: ProgressAnimatedDrawable? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    @TargetApi(23)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr) {

        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        this.mGradientDrawable =  context.getDrawable(R.drawable.button_default_shape) as StateListDrawable
        this.mState = State.IDLE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mState === State.PROGRESS && !mIsMorphingInProgress) {
            drawIndeterminateProgress(canvas)
        }
    }

    private fun drawIndeterminateProgress(canvas: Canvas) {
        if (mAnimatedDrawable == null || !mAnimatedDrawable!!.isRunning) {

            val arcWidth = 15

            mAnimatedDrawable = ProgressAnimatedDrawable(this,
                    arcWidth.toFloat(),
                    Color.WHITE)

            val offset = (width - height) / 2

            val right = width - offset
            val bottom = height
            val top = 0

            mAnimatedDrawable!!.setBounds(offset, top, right, bottom)
            mAnimatedDrawable!!.setCallback(this)
            mAnimatedDrawable!!.start()
        } else {
            mAnimatedDrawable!!.draw(canvas)
        }
    }

    fun startAnimation() {
        if (mState !== State.IDLE) {
            return
        }

        val initialWidth = width
        val initialHeight = height

        val initialCornerRadius = 0f
        val finalCornerRadius = 1000f

        mState = State.PROGRESS
        mIsMorphingInProgress = true
        this.text = null
        isClickable = false

        val toWidth = 175 //some random value...

        val cornerAnimation = ObjectAnimator.ofFloat(mGradientDrawable,
                "cornerRadius",
                initialCornerRadius,
                finalCornerRadius)

        val widthAnimation = ValueAnimator.ofInt(initialWidth, toWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = `val`
            setLayoutParams(layoutParams)
        }

        val heightAnimation = ValueAnimator.ofInt(initialHeight, toWidth)
        heightAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.height = `val`
            setLayoutParams(layoutParams)
        }

        mMorphingAnimatorSet = AnimatorSet()
        mMorphingAnimatorSet.setDuration(300)
        mMorphingAnimatorSet.playTogether(cornerAnimation, widthAnimation, heightAnimation)
        mMorphingAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mIsMorphingInProgress = false
            }
        })
        mMorphingAnimatorSet.start()
    }
}