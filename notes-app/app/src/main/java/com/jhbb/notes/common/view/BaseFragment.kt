package com.jhbb.notes.common.view

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jhbb.domain.model.ErrorModel
import com.jhbb.domain.model.Network
import com.jhbb.notes.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseFragment : Fragment() {

    private var baseActivity: BaseActivity? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity = activity as? BaseActivity
    }

    protected fun showLoadingBar() {
        baseActivity?.binding?.loadingBar?.visibility = View.VISIBLE
    }

    protected fun hideLoadingBar() {
        baseActivity?.binding?.loadingBar?.visibility = View.INVISIBLE
    }

    protected fun showMessage(@StringRes message: Int) {
        baseActivity?.binding?.let {
            loadingBar?.visibility = View.VISIBLE
            Snackbar.make(it.fragmentContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    protected fun showMessageWithAction(@StringRes message: Int, @StringRes action: Int, function: () -> Unit) {
        baseActivity?.binding?.let {
            loadingBar?.visibility = View.VISIBLE
            Snackbar.make(it.fragmentContainer, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(action) { function.invoke() }
                .show()
        }
    }

    protected fun showDialog(@StringRes title: Int,
                   @StringRes message: Int,
                   @StringRes action: Int,
                   function: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(action) { _, _ -> function.invoke() }
            .show()
    }

    protected open fun handleError(fallback: () -> Unit, error: ErrorModel) {
        when (error) {
            is Network -> showDialog(
                R.string.error_title_no_connection,
                R.string.failure_no_connection,
                R.string.action_retry,
                fallback)

            else -> showDialog(
                R.string.error_title_generic,
                R.string.failure_message_generic,
                R.string.action_retry,
                fallback)
        }
    }
}