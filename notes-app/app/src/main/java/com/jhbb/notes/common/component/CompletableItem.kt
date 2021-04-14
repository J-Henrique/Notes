package com.jhbb.notes.common.component

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.jhbb.notes.R
import com.jhbb.notes.common.extension.tintTextAnimation
import com.jhbb.notes.databinding.CompletableItemBinding

class CompletableItem(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val primaryColor = ContextCompat.getColor(context, R.color.primaryTextColor)
    private val secondaryColor = ContextCompat.getColor(context, R.color.secondaryColor)
    private var index: Int? = null
    private var callbackAction: OnCheckListener? = null
    private var binding =
        CompletableItemBinding.inflate(LayoutInflater.from(context), this)

    interface OnCheckListener { fun checked(index: Int?) }

    init {
        attrs?.let {
            context.theme.obtainStyledAttributes(
                it, R.styleable.CompletableItem, 0, 0).apply {
                binding.description.text = this.getString(R.styleable.CompletableItem_taskText)
                recycle()
            }
        }

        binding.apply {
            with(root) {
                background = ContextCompat.getDrawable(context, R.drawable.round_corner_shape)
                layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80)
            }

            completed.setOnClickListener {
                tintText((it as CheckBox).isChecked)
                callbackAction?.checked(index)
            }
        }
    }

    fun setText(text: String) {
        binding.description.text = text
        invalidate()
        requestLayout()
    }

    fun setStatus(status: Boolean) {
        binding.completed.isChecked = status
        binding.description.run {
            paintFlags = if (status) {
                setTextColor(secondaryColor)
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                setTextColor(primaryColor)
                0
            }
        }
        invalidate()
        requestLayout()
    }

    fun setIndex(index: Int) {
        this.index = index
    }

    fun setCallback(action: OnCheckListener) {
        callbackAction = action
    }

    private fun tintText(isChecked: Boolean) {
        val animDuration = 400L

        if (isChecked) {
            binding.description.run {
                tintTextAnimation(primaryColor, secondaryColor, animDuration)
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            binding.description.run {
                tintTextAnimation(secondaryColor, primaryColor, animDuration)
                paintFlags = 0
            }
        }
    }
}