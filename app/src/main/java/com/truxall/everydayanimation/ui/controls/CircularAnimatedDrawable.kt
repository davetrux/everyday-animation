package com.truxall.everydayanimation.ui.controls

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.graphics.drawable.Drawable
import android.view.View
import timber.log.Timber


class CircularAnimatedDrawable(view: View, private val mBorderWidth: Float, arcColor: Int) : Drawable(), Animatable {

    private val ANGLE_INTERPOLATOR = LinearInterpolator()
    private val SWEEP_INTERPOLATOR = DecelerateInterpolator()
    private val ANGLE_ANIMATOR_DURATION = 2000L
    private val SWEEP_ANIMATOR_DURATION = 700L
    private val MIN_SWEEP_ANGLE = 50f

    private var mValueAnimatorAngle: ValueAnimator? = null
    private var mValueAnimatorSweep: ValueAnimator? = null

    private val fBounds = RectF()
    private val mPaint: Paint = Paint()
    private var mCurrentGlobalAngle: Float = 0f
    private var mCurrentSweepAngle: Float = 0f
    private var mCurrentGlobalAngleOffset: Float = 0f
    private val mAnimatorSet: AnimatorSet?
    private var running: Boolean = false
    private var mModeAppearing: Boolean = false

    private var shouldDraw: Boolean = true
    private val mAnimatedView: View

    init {
        this.mAnimatedView = view
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth
        mPaint.color = arcColor

        this.shouldDraw = true
        this.setupAnimations()
        this.mAnimatorSet = AnimatorSet()
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        fBounds.left = bounds.left + mBorderWidth / 2f + .5f
        fBounds.right = bounds.right - mBorderWidth / 2f - .5f
        fBounds.top = bounds.top + mBorderWidth / 2f + .5f
        fBounds.bottom = bounds.bottom - mBorderWidth / 2f - .5f
    }

    override fun start() {
        if (running) {
            return
        }

        this.running = true
        this.mAnimatorSet!!.playTogether(this.mValueAnimatorAngle, this.mValueAnimatorSweep)
        this.mAnimatorSet.start()
    }


    override fun stop() {
        if (!running) {
            return
        }

        this.running = false
        this.mAnimatorSet!!.cancel()
    }

    fun setLoadingBarColor(color: Int) {
        mPaint.color = color
    }

    /**
     * Method called when the drawable is going to draw itself.
     * @param canvas
     */
    override fun draw(canvas: Canvas) {
        this.mAnimatedView.invalidate()
        var startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset
        var sweepAngle = mCurrentSweepAngle

        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE
        } else {
            sweepAngle += MIN_SWEEP_ANGLE
        }

        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    private fun setupAnimations() {
        this.mValueAnimatorAngle = ValueAnimator.ofFloat(0f, 360f)
        this.mValueAnimatorAngle!!.interpolator = ANGLE_INTERPOLATOR
        this.mValueAnimatorAngle!!.duration = ANGLE_ANIMATOR_DURATION
        this.mValueAnimatorAngle!!.repeatCount = ValueAnimator.INFINITE

        this.mValueAnimatorAngle!!.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            mCurrentGlobalAngle = animatedValue
        }

        this.mValueAnimatorSweep = ValueAnimator.ofFloat(0f, 360f - 2f * MIN_SWEEP_ANGLE)
        this.mValueAnimatorSweep!!.interpolator = SWEEP_INTERPOLATOR
        this.mValueAnimatorSweep!!.duration = SWEEP_ANIMATOR_DURATION
        this.mValueAnimatorSweep!!.repeatCount = ValueAnimator.INFINITE
        this.mValueAnimatorSweep!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                toggleAppearingMode()
                shouldDraw = false
            }
        })
        this.mValueAnimatorSweep!!.addUpdateListener { animation ->
            mCurrentSweepAngle = animation.animatedValue as Float

            if (mCurrentSweepAngle < 5) {
                shouldDraw = true
            }

            if (shouldDraw) {
                mAnimatedView.invalidate()
            }
        }
    }

    /**
     * This method is called in every repetition of the animation, so the animation make the sweep
     * growing and then make it shrinking.
     */
    private fun toggleAppearingMode() {
        this.mModeAppearing = !this.mModeAppearing

        if (this.mModeAppearing) {
            this.mCurrentGlobalAngleOffset = (this.mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 0.5f) % 360
        }
    }

    fun dispose() {
        if (mValueAnimatorAngle != null) {
            mValueAnimatorAngle!!.end()
            mValueAnimatorAngle!!.removeAllUpdateListeners()
            mValueAnimatorAngle!!.cancel()
        }

        mValueAnimatorAngle = null

        if (mValueAnimatorSweep != null) {
            mValueAnimatorSweep!!.end()
            mValueAnimatorSweep!!.removeAllUpdateListeners()
            mValueAnimatorSweep!!.cancel()
        }

        mValueAnimatorSweep = null

        if (mAnimatorSet != null) {
            mAnimatorSet.end()
            mAnimatorSet.cancel()
        }
    }

    companion object {
        const val MIN_PROGRESS = 0
        const val MAX_PROGRESS = 100
    }
}


