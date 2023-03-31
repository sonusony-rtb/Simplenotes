package com.rtb.simplenotes.koin

import com.rtb.simplenotes.db.NotesService
import com.rtb.simplenotes.ui.main.model.MyObjectBox
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        MyObjectBox.builder().androidContext(androidContext()).build()
    }

    single { NotesService() }
}