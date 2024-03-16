package com.example.lab2android.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class PasswordDbHelper(context: Context) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${DB.DBEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} TEXT PRIMARY KEY," +
                "${DB.DBEntry.PASSWORD} TEXT," +
                "${DB.DBEntry.SHOW_PASSWORD} BOOL)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DB.DBEntry.TABLE_NAME}"
    private val SQL_DELETE_DATA = "DELETE FROM ${DB.DBEntry.TABLE_NAME};"


    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(SQL_CREATE_ENTRIES)
        } catch (e: Exception) {
            print("The table was already created")
            print(e.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun onDeleteData(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_DATA)

    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}