package com.jhbb.notes.core.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.add(fragment: Fragment, container: Int) {
    supportFragmentManager.beginTransaction()
        .add(container, fragment)
        .commit()
}