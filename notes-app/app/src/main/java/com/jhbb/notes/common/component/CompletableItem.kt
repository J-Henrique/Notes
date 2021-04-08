package com.jhbb.notes.common.component

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.jhbb.notes.R
import com.jhbb.notes.common.extension.tintTextAnimation
import kotlinx.android.synthetic.main.completable_item.view.*

class CompletableItem : LinearLayout {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CompletableItem, 0, 0).apply {
            description.text = this.getString(R.styleable.CompletableItem_taskText)
            recycle()
        }
    }
    constructor(context: Context, callback: OnCheckListener?) : super(context) {
        this.callbackAction = callback
    }

    private var callbackAction: OnCheckListener? = null

    private val primaryColor = ContextCompat.getColor(context, R.color.primaryTextColor)
    private val secondaryColor = ContextCompat.getColor(context, R.color.secondaryColor)
    private var index: Int? = null

    interface OnCheckListener { fun checked(index: Int?) }

    init {
        inflate(context, R.layout.completable_item, this)

        completed.setOnClickListener {
            tintText((it as CheckBox).isChecked)
            callbackAction?.checked(index)
        }
    }

    fun setText(text: String) {
        description.text = text
        invalidate()
        requestLayout()
    }

    fun setStatus(status: Boolean) {
        completed.isChecked = status
        description.setTextColor(if (status) secondaryColor else primaryColor)
        invalidate()
        requestLayout()
    }

    fun setIndex(index: Int) {
        this.index = index
    }

    private fun tintText(isChecked: Boolean) {
        val animDuration = 400L

        if (isChecked) {
            description.tintTextAnimation(primaryColor, secondaryColor, animDuration)
        } else {
            description.tintTextAnimation(secondaryColor, primaryColor, animDuration)
        }
    }
}