package com.example.lab2android.activities

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2android.R
import com.example.lab2android.fragments.FirstFragment
import com.example.lab2android.storage.DB
import com.example.lab2android.storage.PasswordDbHelper

class SecondActivity : AppCompatActivity() {

    private lateinit var resultTextSQLite: TextView
    private lateinit var goBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        resultTextSQLite = findViewById(R.id.result_sqlite)
        goBackButton = findViewById(R.id.button_go_back)

        resultTextSQLite.text = ""

        val dbHelper = PasswordDbHelper(applicationContext)
        val db = dbHelper.readableDatabase

        displayPasswordFromDatabase(db)
        setGoBackClickListener()
    }

    private fun displayPasswordFromDatabase(db: SQLiteDatabase) {
        val projection = arrayOf(BaseColumns._ID, DB.DBEntry.PASSWORD, DB.DBEntry.SHOW_PASSWORD)
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = db.query(
            DB.DBEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder,
            "1"
        )

        cursor.moveToFirst()

        val index = cursor.getColumnIndex(DB.DBEntry.PASSWORD)
        if (index > -1) {
            val text = cursor.getString(index)
            resultTextSQLite.text = text
        }

        if (resultTextSQLite.text.isEmpty()) {
            resultTextSQLite.text = getString(R.string.no_data)
        }

        cursor.close()
    }

    private fun setGoBackClickListener() {
        goBackButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.root_container,
                    FirstFragment()
                )
                .commit()
        }
    }
}
