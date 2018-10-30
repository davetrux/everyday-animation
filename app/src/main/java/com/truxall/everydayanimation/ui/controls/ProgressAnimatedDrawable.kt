package com.truxall.everydayanimation.ui.controls

import android.animation.ValueAnimator
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.R.string.cancel
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.ColorFilter
import androidx.core.view.ViewCompat.setAlpha
import android.animation.Animator
import android.animation.AnimatorListenerAdapter


class ProgressAnimatedDrawable(view: View, borderWidth: Float, arcColor: Int) : Drawable(), Animatable {

    private var mValueAnimatorAngle: ValueAnimator? = null
    private var mValueAnimatorSweep: ValueAnimator? = null
    private val ANGLE_INTERPOLATOR = LinearInterpolator()
    private val SWEEP_INTERPOLATOR = DecelerateInterpolator()
    private val ANGLE_ANIMATOR_DURATION = 2000L
    private val SWEEP_ANIMATOR_DURATION = 900L
    private val MIN_SWEEP_ANGLE = 30f

    private val fBounds = RectF()
    private var mPaint: Paint
    private var mAnimatedView: View

    private var mBorderWidth: Float = 0.toFloat()
    private var mCurrentGlobalAngle: Float = 0.toFloat()
    private var mCurrentSweepAngle: Float = 0.toFloat()
    private var mCurrentGlobalAngleOffset: Float = 0.toFloat()

    private var mModeAppearing: Boolean = false
    var mRunning: Boolean = false

    init {
        this.mAnimatedView = view
        this.mBorderWidth = borderWidth

        this.mPaint = Paint()
        this.mPaint.setAntiAlias(true)
        this.mPaint.setStyle(Paint.Style.STROKE)
        this.mPaint.setStrokeWidth(borderWidth)
        this.mPaint.setColor(arcColor)

        this.setupAnimations()
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        fBounds.left = bounds.left + mBorderWidth / 2f + .5f
        fBounds.right = bounds.right - mBorderWidth / 2f - .5f
        fBounds.top = bounds.top + mBorderWidth / 2f + .5f
        fBounds.bottom = bounds.bottom - mBorderWidth / 2f - .5f
    }

    override fun isRunning(): Boolean {
        return this.mRunning
    }

    override fun start() {
        if (mRunning) {
            return
        }

        mRunning = true
        mValueAnimatorAngle!!.start()
        mValueAnimatorSweep!!.start()
    }

    override fun stop() {
        if (!this.mRunning) {
            return
        }

        this.mRunning = false
        mValueAnimatorAngle!!.cancel()
        mValueAnimatorSweep!!.cancel()
    }

    override fun draw(canvas: Canvas) {
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
        mValueAnimatorAngle = ValueAnimator.ofFloat(0f, 360f)
        mValueAnimatorAngle!!.interpolator = ANGLE_INTERPOLATOR
        mValueAnimatorAngle!!.setDuration(ANGLE_ANIMATOR_DURATION)
        mValueAnimatorAngle!!.repeatCount = ValueAnimator.INFINITE
        mValueAnimatorAngle!!.addUpdateListener { animation ->
            mCurrentGlobalAngle = animation.animatedValue as Float
            mAnimatedView.invalidate()
        }

        mValueAnimatorSweep = ValueAnimator.ofFloat(0f, 360f - 2 * MIN_SWEEP_ANGLE)
        mValueAnimatorSweep!!.interpolator = SWEEP_INTERPOLATOR
        mValueAnimatorSweep!!.setDuration(SWEEP_ANIMATOR_DURATION)
        mValueAnimatorSweep!!.repeatCount = ValueAnimator.INFINITE
        mValueAnimatorSweep!!.addUpdateListener { animation ->
            mCurrentSweepAngle = animation.animatedValue as Float
            invalidateSelf()
        }
        mValueAnimatorSweep!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator) {
                toggleAppearingMode()
            }
        })
    }

    private fun toggleAppearingMode() {
        this.mModeAppearing = !this.mModeAppearing

        if (this.mModeAppearing) {
            this.mCurrentGlobalAngleOffset = (this.mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 0.5f) % 360
        }
    }
}