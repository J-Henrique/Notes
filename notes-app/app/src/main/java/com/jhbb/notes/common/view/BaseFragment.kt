package com.jhbb.notes.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
        activity?.loadingBar?.visibility = View.VISIBLE
    }

    fun hideLoadingBar() {
        activity?.loadingBar?.visibility = View.INVISIBLE
    }

    fun showMessage(@StringRes message: Int) {
        activity?.loadingBar?.visibility = View.INVISIBLE
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    fun showMessageWithAction(@StringRes message: Int, @StringRes action: Int, function: () -> Unit) {
        activity?.loadingBar?.visibility = View.INVISIBLE
        if (activity is BaseActivity) {
            Snackbar.make((activity as BaseActivity).fragmentContainer, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(action) { function.invoke() }
                .show()
        }
    }

    fun showDialog(@StringRes title: Int,
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