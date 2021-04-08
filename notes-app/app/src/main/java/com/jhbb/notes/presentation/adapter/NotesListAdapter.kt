package com.jhbb.notes.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.common.component.CompletableItem

class NotesListAdapter(private val notesList: MutableList<NoteModel> = mutableListOf(),
                       private val checkEvent: (NoteModel) -> Unit)
    : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = CompletableItem(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindView(notesList[position])
    }

    override fun getItemCount() = notesList.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun refreshList(notes: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(note: NoteModel) {
            with(itemView as CompletableItem) {
                this.setText(note.description)
                this.setStatus(note.completed)
                this.setIndex(this@NoteViewHolder.adapterPosition)
                this.setCallback(object : CompletableItem.OnCheckListener {
                    override fun checked(index: Int?) {
                        index?.let { checkEvent(notesList[index]) }
                    }
                })
            }
        }
    }
}