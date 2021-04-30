package com.jhbb.notes.common.view

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jhbb.domain.model.ErrorModel
import com.jhbb.domain.model.Network
import com.jhbb.notes.R

abstract class BaseFragment : Fragment() {

    protected open fun handleError(fallback: () -> Unit, error: ErrorModel) {
        when (error) {
            is Network -> showDialog(
                R.string.error_title_no_connection,
                R.string.failure_no_connection,
                R.string.action_retry,
                fallback
            )

            else -> showDialog(
                R.string.error_title_generic,
                R.string.failure_message_generic,
                R.string.action_retry,
                fallback
            )
        }
    }

    private fun showDialog(@StringRes title: Int,
                           @StringRes message: Int,
                           @StringRes action: Int,
                           function: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(action) { _, _ -> function.invoke() }
            .show()
    }
}