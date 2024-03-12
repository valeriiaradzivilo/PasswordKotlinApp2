package com.example.lab2android.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab2android.R
import com.example.lab2android.activities.SecondActivity
import com.example.lab2android.storage.DB
import com.example.lab2android.storage.PasswordDbHelper

class FirstFragment : Fragment() {

    private var passwordField: EditText? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var okButton: Button
    private lateinit var openDbButton: Button
    private lateinit var storePassword: CheckBox

    private var onOkClick: (String) -> Unit = {}

    fun setOnClickListener(listener: (String) -> Unit) {
        onOkClick = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        passwordField = null
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var storePassword: Boolean = false

        initComponents(view)

        clean()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isEmpty = isTextFieldEmpty()
            if (isEmpty) {
                radioGroup.check(R.id.radio_button_visible)
                updateInputType(true)
            } else {
                updateInputType(checkedId == R.id.radio_button_visible)
            }
        }

        okButton.setOnClickListener {
            if (!isTextFieldEmpty() && passwordField != null) {
                onOkClick(passwordField!!.text.toString())
                if (passwordField != null && storePassword) {
                    storePasswordInDatabase(passwordField!!.text.toString())
                }
                clean()
            }
        }

        this.storePassword.setOnClickListener {
            storePassword = this.storePassword.isChecked
        }

        openDbButton.setOnClickListener {
            val intent = Intent(requireContext(), SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents(view: View) {
        passwordField = view.findViewById(R.id.password_text_field)
        radioGroup = view.findViewById(R.id.radio_group)
        okButton = view.findViewById(R.id.button_ok)
        openDbButton = view.findViewById(R.id.open_button)
        storePassword = view.findViewById(R.id.storePasswordCheckBox)
    }

    private fun updateInputType(isPasswordVisible: Boolean) {
        if (passwordField != null) {
            passwordField!!.inputType =
                if (isPasswordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            passwordField!!.setSelection(passwordField!!.text.length)
        }
    }

    private fun isTextFieldEmpty(): Boolean {
        if (passwordField == null) return true
        val isEmpty = passwordField!!.text.isEmpty()
        if (isEmpty) {
            showToast(getString(R.string.fill_form_message))
        }
        return isEmpty
    }

    private fun storePasswordInDatabase(password: String) {
        val dbHelper = PasswordDbHelper(requireContext())
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DB.DBEntry.PASSWORD, password)
            put(
                DB.DBEntry.SHOW_PASSWORD,
                radioGroup.checkedRadioButtonId == R.id.radio_button_visible
            )
        }

        try {
            val newRowId = db?.insert(DB.DBEntry.TABLE_NAME, null, values)
            showMessage(newRowId != null)

        } catch (e: Exception) {
            showMessage(false)
            print(e.toString())
        }
    }

    private fun showMessage(isSuccess: Boolean) {
        val message = if (isSuccess) getString(R.string.store_password_success)
        else getString(R.string.store_password_failure)
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    fun clean() {
        passwordField?.text?.clear()
    }
}
