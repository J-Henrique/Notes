package com.jhbb.notes.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.notes.R
import com.jhbb.notes.databinding.ActivityBaseBinding

abstract class BaseActivity : AppCompatActivity() {

    lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment())
            .commit()
    }

    abstract fun fragment(): BaseFragment

    fun replaceFragment(destination: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragmentContainer, destination)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment() {
        supportFragmentManager
            .popBackStack()
    }
}
