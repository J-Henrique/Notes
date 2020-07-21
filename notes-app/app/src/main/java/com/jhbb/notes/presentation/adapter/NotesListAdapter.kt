package com.jhbb.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhbb.notes.R
import com.jhbb.notes.presentation.vo.NoteViewObject
import kotlinx.android.synthetic.main.item_note.view.*

class NotesListAdapter(private val notesList: List<NoteViewObject>,
                       private val checkEvent: (NoteViewObject) -> Unit)
    : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        val viewHolder = NoteViewHolder(view)
        viewHolder.itemView.completed.setOnClickListener {
            checkEvent.invoke(notesList[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindView(notesList[position])
    }

    override fun getItemCount() = notesList.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(note: NoteViewObject) {
            with(itemView) {
                this.description.text = note.description
                this.completed.isChecked = note.completed
            }
        }
    }
}