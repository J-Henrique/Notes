package com.jhbb.notes.core

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.notes.R
import com.jhbb.notes.api.apiModule
import com.jhbb.notes.presentation.viewmodel.viewModelModule
import com.jhbb.notes.repository.appModule
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.core.context.loadKoinModules

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)

        loadKoinModules()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment())
            .commit()
    }

    private fun loadKoinModules() {
        loadKoinModules(listOf(appModule, viewModelModule, apiModule))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    abstract fun fragment(): BaseFragment
}
