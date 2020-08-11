package com.jhbb.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.jhbb.notes.R
import com.jhbb.notes.presentation.vo.NoteVO
import kotlinx.android.synthetic.main.item_note.view.*

class NotesListAdapter(private val notesList: MutableList<NoteVO> = mutableListOf(),
                       private val checkEvent: (NoteVO) -> Unit)
    : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        val viewHolder = NoteViewHolder(view)
        viewHolder.itemView.completed.setOnClickListener {
            checkEvent(notesList[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        tracker?.let {
            holder.bindView(notesList[position], it.isSelected(position.toLong()))
        }
    }

    override fun getItemCount() = notesList.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun refreshList(notes: List<NoteVO>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(note: NoteVO, isActivated: Boolean = false) {
            with(itemView) {
                this.description.text = note.description
                this.completed.isChecked = note.completed
                itemView.isActivated = isActivated
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }
}