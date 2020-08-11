package com.jhbb.notes.core

import android.view.View

class ViewAnimation {

    companion object {
        fun rotateFab(v: View, rotate: Boolean): Boolean {
            v.animate()
                .setDuration(200)
                .rotation(if (rotate) 135f else 0f)

            return rotate
        }
    }
}