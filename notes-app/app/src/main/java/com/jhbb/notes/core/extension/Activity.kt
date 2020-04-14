package com.jhbb.notes.core.extension

import android.app.Activity
import com.jhbb.notes.core.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*

fun Activity.progressBarState(visibilityState: Int) {
    if (this is BaseActivity) {
        this.loadingBar.visibility = visibilityState
    }
}