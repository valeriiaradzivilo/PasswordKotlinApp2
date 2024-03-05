package com.example.lab2android.fragments


import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab2android.R

class FirstFragment : Fragment() {


    private var editText: EditText? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var button: Button

    private lateinit var onOkClick: (String) -> Unit?
    fun setOnClickListener(listener: (String) -> Unit) {
        onOkClick = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (editText != null)
            editText!!.text?.clear() // Add this line to clear the text

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

