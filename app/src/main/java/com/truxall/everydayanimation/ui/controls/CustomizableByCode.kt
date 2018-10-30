package com.truxall.everydayanimation.ui.controls

internal interface CustomizableByCode {
    fun setSpinningBarWidth(width: Float)
    fun setSpinningBarColor(color: Int)
    fun setDoneColor(color: Int)
    fun setPaddingProgress(padding: Float)
    fun setInitialHeight(height: Int)
    fun setInitialCornerRadius(radius: Float)
    fun setFinalCornerRadius(radius: Float)
}

