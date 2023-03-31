package com.rtb.simplenotes.ui.main.view

import android.os.Bundle
import com.rtb.simplenotes.baseclasses.BaseActivity
import com.rtb.simplenotes.databinding.ActivityAddNoteBinding
import com.rtb.simplenotes.ui.main.model.Note
import com.rtb.simplenotes.ui.main.viewmodel.MainViewModel
import com.rtb.simplenotes.utils.Params

class AddNoteActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    private lateinit var binding: ActivityAddNoteBinding
    private var filledNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater).also { setContentView(it.root) }
        init()
    }

    private fun init() {
        filledNote = intent.getParcelableExtra<Note?>(Params.NOTE)?.also {
            binding.title.setText(it.title)
            binding.description.setText(it.description)
        }
        binding.save.setOnClickListener {
            saveNote()
            onBackPressed()
        }
    }

    private fun saveNote() {
        if (binding.title.text.isNotEmpty() || binding.description.text.isNotEmpty()) {
            filledNote?.let {
                viewModel.saveNote(it.apply {
                    title = binding.title.text.toString().trim()
                    description = binding.description.text.toString().trim()
                })
            } ?: run {
                viewModel.saveNote(Note(title = binding.title.text.toString().trim(), description = binding.description.text.toString().trim()))
            }
        }
    }
}