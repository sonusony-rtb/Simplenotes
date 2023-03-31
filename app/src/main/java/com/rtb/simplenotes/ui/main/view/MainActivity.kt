package com.rtb.simplenotes.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.rtb.andbeyondmedia.banners.BannerAdListener
import com.rtb.andbeyondmedia.common.AdRequest
import com.rtb.simplenotes.adapter.NotesAdapter
import com.rtb.simplenotes.baseclasses.BaseActivity
import com.rtb.simplenotes.databinding.ActivityMainBinding
import com.rtb.simplenotes.ui.main.model.Note
import com.rtb.simplenotes.ui.main.viewmodel.MainViewModel
import com.rtb.simplenotes.utils.Params
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        init()
        getNotes()
        loadAd()
    }

    private fun init() {
        binding.notes.apply {
            val layoutManager: GridLayoutManager by inject()
            this.layoutManager = layoutManager
            this@MainActivity.adapter = NotesAdapter(this@MainActivity, listOf(), this@MainActivity::openNote, this@MainActivity::deleteNote)
            this.adapter = this@MainActivity.adapter
        }
        binding.add.setOnClickListener { startNewActivity(AddNoteActivity::class.java) }
    }

    private fun getNotes() {
        viewModel.getRealTimeNotes().observe(this) { notes ->
            binding.createFirstNote.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            adapter.addNotes(notes)
        }
    }

    private fun openNote(note: Note) {
        startNewActivity(Intent(this, AddNoteActivity::class.java).putExtra(
                Params.NOTE, note
        ))
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note.objectBoxID)
    }

    private fun loadAd() {
        val adRequest = AdRequest().Builder().build()
        binding.adView.loadAd(adRequest)

        binding.adView.setAdListener(object: BannerAdListener {
            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }

            override fun onAdFailedToLoad(error: String) {
                Log.d(TAG, "onAdFailedToLoad: $error")
            }

            override fun onAdImpression() {
            }

            override fun onAdLoaded() {
            }

            override fun onAdOpened() {
            }
        })

    }
}