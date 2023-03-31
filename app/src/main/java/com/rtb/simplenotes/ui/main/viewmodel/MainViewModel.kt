package com.rtb.simplenotes.ui.main.viewmodel

import androidx.lifecycle.LiveData
import com.rtb.simplenotes.baseclasses.BaseViewModel
import com.rtb.simplenotes.db.*
import com.rtb.simplenotes.ui.main.model.Note

class MainViewModel(private val noteService: NotesService) : BaseViewModel() {

    fun getAllNotes(): List<Note> {
        return noteService.getBox(Note::class.java).getAllOnce()
    }

    fun saveNote(note: Note) {
        noteService.getBox(Note::class.java).store(note)
    }

    fun getRealTimeNotes(): LiveData<List<Note>> {
        return noteService.getBox(Note::class.java).getAllRealtime()
    }

    fun deleteNote(id: Long) {
        noteService.getBox(Note::class.java).delete(id)
    }
}