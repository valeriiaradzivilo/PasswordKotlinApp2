package com.example.lab2android.fragments


import android.content.ContentValues
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
import com.example.lab2android.storage.DB
import com.example.lab2android.storage.PasswordDbHelper

class FirstFragment : Fragment() {


    private var editText: EditText? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var button: Button
    private lateinit var checkBox: CheckBox

    private lateinit var onOkClick: (String) -> Unit?
    fun setOnClickListener(listener: (String) -> Unit) {
        onOkClick = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (editText != null)
            editText!!.text?.clear()

        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (editText != null)
            editText!!.text?.clear() // Add this line to clear the text
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.text_field)
        radioGroup = view.findViewById(R.id.radio_group)
        button = view.findViewById(R.id.button_ok)
        checkBox = view.findViewById(R.id.storePasswordCheckBox)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val isEmpty = isTextFieldEmpty()
            if (isEmpty) {
                group.check(R.id.radio_button_visible)
                updateInputType(true)
            } else
                updateInputType(checkedId == R.id.radio_button_visible)
        }

        button.setOnClickListener {
            val isEmpty = isTextFieldEmpty()
            if (!isEmpty && editText != null)
                onOkClick(editText!!.text.toString())
        }

        checkBox.setOnClickListener {
            if (editText != null) {
                var text = editText!!.text.toString()
                val dbHelper = PasswordDbHelper(requireContext())
                // Gets the data repository in write mode
                val db = dbHelper.writableDatabase

// Create a new map of values, where column names are the keys
                val values = ContentValues().apply {
                    put(DB.DBEntry.PASSWORD, text)
                    put(
                        DB.DBEntry.SHOW_PASSWORD,
                        radioGroup.checkedRadioButtonId == R.id.radio_button_visible
                    )
                }

// Insert the new row, returning the primary key value of the new row
                val newRowId = db?.insert(DB.DBEntry.TABLE_NAME, null, values)
                if (newRowId != null) {
                    Toast.makeText(
                        requireActivity(),
                        "The password was stored successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "The password was NOT stored successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
        }
    }

    private fun updateInputType(isPasswordVisible: Boolean) {
        if (editText != null) {
            editText!!.inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            editText!!.setSelection(editText!!.text.length)
        }
    }

    private fun isTextFieldEmpty(): Boolean {
        if (editText == null) return true
        val isEmpty = editText!!.text.isEmpty()
        if (isEmpty) {
            Toast.makeText(requireActivity(), "Please, fill in the form )", Toast.LENGTH_SHORT)
                .show()

        }
        return isEmpty
    }

    fun clean() {
        if (editText != null)
            editText!!.text?.clear()
    }


}

