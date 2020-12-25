package com.jhbb.notes.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.notes.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment())
            .commit()
    }

    abstract fun fragment(): BaseFragment

    fun replaceFragment(destination: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, destination)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment() {
        supportFragmentManager
            .popBackStack()
    }
}
