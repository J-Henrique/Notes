package com.jhbb.notes.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jhbb.notes.databinding.ActivityMainBinding
import com.jhbb.notes.presentation.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<NotesViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = supportFragmentManager
            .findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        val navigationController = host.navController
        val appBarConfiguration = AppBarConfiguration(navigationController.graph)

        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
    }
}
