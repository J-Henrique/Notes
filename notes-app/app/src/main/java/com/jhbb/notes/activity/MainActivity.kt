package com.jhbb.notes.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.notes.R
import com.jhbb.notes.viewmodel.NotesViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<NotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getNotes()
    }
}
