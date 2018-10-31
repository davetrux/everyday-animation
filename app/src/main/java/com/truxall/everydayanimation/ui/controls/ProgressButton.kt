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
import android.graphics.drawable.Drawable
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
    private var mInitialHeight: Int = 0
    private var mInitialWidth: Int = 0
    private val mInitialCornerRadius: Float = 0f
    private val mFinalCornerRadius: Float = 1000f
    private lateinit var mButtonText: CharSequence
    private lateinit var mDrawables: Array<Drawable>

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
        this.mDrawables = this.compoundDrawablesRelative
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mState === State.PROGRESS && !mIsMorphingInProgress) {
            drawIndeterminateProgress(canvas)
        }
    }

    private fun drawIndeterminateProgress(canvas: Canvas) {
        if (mAnimatedDrawable == null || !mAnimatedDrawable!!.isRunning) {

            val arcWidth = 15f

            mAnimatedDrawable = ProgressAnimatedDrawable(this, arcWidth, Color.WHITE)

            val offset = (width - height) / 2

            val right = width - offset
            val bottom = height
            val top = 0

            mAnimatedDrawable!!.setBounds(offset, top, right, bottom)
            mAnimatedDrawable!!.callback = this
            mAnimatedDrawable!!.start()
        } else {
            mAnimatedDrawable!!.draw(canvas)
        }
    }

    fun stopAnimation() {
        if (mState == State.PROGRESS && !mIsMorphingInProgress) {
            mState = State.IDLE
            mAnimatedDrawable!!.stop()
        }
    }

    fun startAnimation() {
        if (mState !== State.IDLE) {
            return
        }

        this.mInitialWidth = width
        this.mInitialHeight = height


        mState = State.PROGRESS
        mIsMorphingInProgress = true

        this.mButtonText = this.text
        this.text = null

        isClickable = false

        val toWidth = 175

        val cornerAnimation = ObjectAnimator.ofFloat(mGradientDrawable,
                "cornerRadius",
                this.mInitialCornerRadius,
                this.mFinalCornerRadius)

        val widthAnimation = ValueAnimator.ofInt(this.mInitialWidth, toWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = animatedValue
            setLayoutParams(layoutParams)
        }

        val heightAnimation = ValueAnimator.ofInt(this.mInitialHeight, toWidth)
        heightAnimation.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.height = animatedValue
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

    fun revertAnimation() {
        if (mAnimatedDrawable != null && mAnimatedDrawable!!.isRunning) {
            stopAnimation()
            this.mState = State.IDLE
        }

        if (mIsMorphingInProgress) {
            mMorphingAnimatorSet!!.cancel()
        }

        isClickable = false

        val fromWidth = width
        val fromHeight = height

        val toHeight = this.mInitialHeight
        val toWidth = this.mInitialWidth

        var cornerAnimation: ObjectAnimator? = null
        cornerAnimation = ObjectAnimator.ofFloat(mGradientDrawable,
                    "cornerRadius",
                    this.mFinalCornerRadius,
                    this.mInitialCornerRadius)


        val widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = animatedValue
            setLayoutParams(layoutParams)
        }

        val heightAnimation = ValueAnimator.ofInt(fromHeight, toHeight)
        heightAnimation.addUpdateListener { valueAnimator ->
            val animatedVal = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.height = animatedVal
            setLayoutParams(layoutParams)
        }

        mMorphingAnimatorSet = AnimatorSet()
        mMorphingAnimatorSet.duration = 300

        mMorphingAnimatorSet!!.playTogether(cornerAnimation, widthAnimation, heightAnimation)

        mMorphingAnimatorSet!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                setClickable(true)
                mIsMorphingInProgress = false
                text = mButtonText
                setCompoundDrawablesRelative(mDrawables[0], mDrawables[1], mDrawables[2], mDrawables[3])
            }
        })

        mIsMorphingInProgress = true
        mMorphingAnimatorSet!!.start()
    }
}