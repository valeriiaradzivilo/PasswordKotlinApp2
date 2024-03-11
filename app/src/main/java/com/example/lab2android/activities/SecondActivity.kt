package com.example.lab2android.activities

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2android.R
import com.example.lab2android.storage.DB
import com.example.lab2android.storage.PasswordDbHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SecondActivity : AppCompatActivity() {

    private lateinit var resultTextSQLite: TextView
    private lateinit var resultShowPasswordSQLite: TextView
    private lateinit var goBackButton: Button
    private lateinit var deleteDataFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        resultTextSQLite = findViewById(R.id.password_result)
        resultShowPasswordSQLite = findViewById(R.id.show_password_result)
        goBackButton = findViewById(R.id.button_go_back)
        deleteDataFAB = findViewById(R.id.delete_db_data_fab)

        resultTextSQLite.text = ""

        val dbHelper = PasswordDbHelper(applicationContext)
        val db = dbHelper.readableDatabase

        displayPasswordFromDatabase(db)
        setGoBackClickListener()

        deleteDataFAB.setOnClickListener {
            dbHelper.onDeleteData(db)
        }
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
        )

        cursor.moveToFirst()

        val index = cursor.getColumnIndex(DB.DBEntry.PASSWORD)
        val indexShow = cursor.getColumnIndex(DB.DBEntry.SHOW_PASSWORD)
        if (index > -1) {
            with(cursor)
            {
                while (moveToNext()) {
                    val text = cursor.getString(index)
                    val showPassword = cursor.getString(indexShow)
                    if (resultTextSQLite.text.isNotEmpty()) {
                        resultTextSQLite.text = resultTextSQLite.text.toString() + "\n" + text
                        resultShowPasswordSQLite.text =
                            resultShowPasswordSQLite.text.toString() + "\n" + showPassword

                    } else {
                        resultTextSQLite.text = text
                        resultShowPasswordSQLite.text = showPassword
                    }


                }
            }

        }

        if (resultTextSQLite.text.trim().isEmpty()) {
            resultTextSQLite.text = getString(R.string.no_data)
        }

        cursor.close()
    }

    private fun setGoBackClickListener() {
        goBackButton.setOnClickListener {
            this.finish()
        }
    }
}
