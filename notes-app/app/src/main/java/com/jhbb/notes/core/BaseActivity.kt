package com.jhbb.notes.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.notes.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment())
            .commit()
    }

    abstract fun fragment(): BaseFragment
}
