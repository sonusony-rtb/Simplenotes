package com.rtb.simplenotes.ui.main.model

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.Parcelize

interface BoxStoreObject {
    var objectBoxID: Long
}

@Entity
@Parcelize
data class Note(
        @Id
        override var objectBoxID: Long = 0,
        var title: String? = null,
        var description: String? = null,
) : BoxStoreObject, Parcelable