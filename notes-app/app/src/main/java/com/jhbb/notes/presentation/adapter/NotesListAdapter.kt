package com.jhbb.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhbb.domain.model.NoteModel
import com.jhbb.notes.R
import kotlinx.android.synthetic.main.item_note.view.*

class NotesListAdapter(private val notesList: MutableList<NoteModel> = mutableListOf(),
                       private val checkEvent: (NoteModel) -> Unit)
    : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

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
            with(itemView) {
                this.description.text = note.description
                this.completed.isChecked = note.completed
            }
        }
    }
}