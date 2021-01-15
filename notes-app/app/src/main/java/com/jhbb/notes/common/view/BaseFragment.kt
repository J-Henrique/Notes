package com.jhbb.notes.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jhbb.domain.model.ErrorModel
import com.jhbb.domain.model.Network
import com.jhbb.notes.R
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

    protected fun showLoadingBar() {
        activity?.loadingBar?.visibility = View.VISIBLE
    }

    protected fun hideLoadingBar() {
        activity?.loadingBar?.visibility = View.INVISIBLE
    }

    protected fun showMessage(@StringRes message: Int) {
        activity?.loadingBar?.visibility = View.INVISIBLE
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    protected fun showMessageWithAction(@StringRes message: Int, @StringRes action: Int, function: () -> Unit) {
        activity?.loadingBar?.visibility = View.INVISIBLE
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_INDEFINITE)
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