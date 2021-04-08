package com.jhbb.notes.common.extension

import android.animation.ObjectAnimator
import android.widget.TextView

fun TextView.tintTextAnimation(colorIn: Int, colorOut: Int, animDuration: Long) {
    ObjectAnimator.ofArgb(
        this, "textColor",
        colorIn, colorOut
    ).apply {
        duration = animDuration
        start()
    }
}