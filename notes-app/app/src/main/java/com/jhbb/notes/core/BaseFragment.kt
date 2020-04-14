package com.jhbb.notes.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jhbb.notes.core.extension.progressBarState
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    fun showLoadingBar() {
        activity?.progressBarState(View.VISIBLE)
    }

    fun hideLoadingBar() {
        activity?.progressBarState(View.INVISIBLE)
    }

    fun showMessage(@StringRes message: Int) {
        activity?.progressBarState(View.INVISIBLE)
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    fun showMessageWithAction(@StringRes message: Int, @StringRes action: Int, function: () -> Unit) {
        activity?.progressBarState(View.INVISIBLE)
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(action) { function.invoke() }
                .show()
        }
    }

    fun showDialog(@StringRes message: Int, @StringRes action: Int, function: () -> Unit) {
        MaterialAlertDialogBuilder(this.context)
            .setTitle(message)
            .setMessage(message)
            .setPositiveButton(action) { _, _ -> function.invoke() }
            .show()
    }
}