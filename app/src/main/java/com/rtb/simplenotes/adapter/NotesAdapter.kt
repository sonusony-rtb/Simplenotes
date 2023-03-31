package com.rtb.simplenotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rtb.simplenotes.databinding.NoteCardLayoutBinding
import com.rtb.simplenotes.ui.main.model.Note

class NotesAdapter(private val context: Context, private var notes: List<Note>, private var open: (Note) -> Unit, private var delete :(Note)->Unit) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(NoteCardLayoutBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.title.text = notes[position].title
        holder.binding.description.text = notes[position].description
        holder.itemView.setOnClickListener { open(notes[position]) }
        holder.binding.delete.setOnClickListener { delete(notes[position]) }
    }

    override fun getItemCount(): Int = notes.size

    fun addNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: NoteCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}