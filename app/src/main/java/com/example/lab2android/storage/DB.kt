package com.example.lab2android.storage

import android.provider.BaseColumns

object DB {
    // Table contents are grouped together in an anonymous object.
    object DBEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val SHOW_PASSWORD = "show"
        const val PASSWORD = "password"
    }
}