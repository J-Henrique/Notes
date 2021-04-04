package com.jhbb.notes.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhbb.notes.common.helper.EspressoIdlingResource
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {
    fun ViewModel.launch(coroutineContext: CoroutineContext, function: suspend () -> Unit) {
        this.viewModelScope.launch(coroutineContext) {
            EspressoIdlingResource.increment()

            try {
                function()
            } finally {
                EspressoIdlingResource.decrement()
            }
        }
    }
}