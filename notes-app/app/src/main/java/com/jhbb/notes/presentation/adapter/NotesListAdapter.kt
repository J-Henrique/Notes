package com.jhbb.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhbb.notes.R
import com.jhbb.notes.presentation.vo.NoteVO
import kotlinx.android.synthetic.main.item_note.view.*

class NotesListAdapter(private val notesList: MutableList<NoteVO> = mutableListOf(),
                       private val checkEvent: (NoteVO) -> Unit)
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

    fun refreshList(notes: List<NoteVO>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(note: NoteVO) {
            with(itemView) {
                this.description.text = note.description
                this.completed.isChecked = note.completed
            }
        }
    }
}