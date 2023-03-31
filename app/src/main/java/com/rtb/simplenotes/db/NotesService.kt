package com.rtb.simplenotes.db

import androidx.lifecycle.LiveData
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotesService : KoinComponent {
    val boxStore: BoxStore by inject()

    fun <T> getBox(model: Class<T>): Box<T> {
        return boxStore.boxFor(model)
    }
}

fun <T> Box<T>.getAllOnce(): List<T> {
    return all
}

fun <T> Box<T>.store(model: T) {
    put(model)
}

inline fun <reified T> Box<T>.getAllRealtime(): LiveData<List<T>> {
    return ObjectBoxLiveData(this.query().build())
}

fun <T> Box<T>.delete(id: Long) {
    remove(id)
}