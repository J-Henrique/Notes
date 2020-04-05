package com.jhbb.notes.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jhbb.notes.R
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NotesListFragment : Fragment() {

    private val viewModel by sharedViewModel<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.notes.observe(viewLifecycleOwner, Observer { println(it) })
    }
}
