package com.rtb.simplenotes.koin

import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.rtb.simplenotes.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        androidContext().getSharedPreferences("${BuildConfig.APPLICATION_ID}_app", Context.MODE_PRIVATE)
    }

    single {
        LocalBroadcastManager.getInstance(androidContext())
    }

    factory {
        val layoutManager = GridLayoutManager(androidContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = 1
        }
        layoutManager
    }
}