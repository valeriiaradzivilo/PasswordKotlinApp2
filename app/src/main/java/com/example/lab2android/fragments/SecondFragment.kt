package com.example.lab2android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lab2android.R

class SecondFragment : Fragment() {

    //    private var onCancelButtonClickListener: OnCancelButtonClickListener? = null
//
//    interface OnCancelButtonClickListener {
//        fun onCancelButtonClick()
//    }
//
//    fun setOnCancelButtonClickListener(listener: OnCancelButtonClickListener) {
//        onCancelButtonClickListener = listener
//    }
//
//    // Implement methods for displaying the result and handling the Cancel button click
//
//    // ...
//
//    private fun handleCancelButtonClick() {
//        // Clear or hide (or remove) the fragment
//        // ...
//
//        onCancelButtonClickListener?.onCancelButtonClick()
//    }
//
    companion object {
        fun newInstance(data: String): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putString("data", data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access UI elements and perform actions if needed
        val resultTextView: TextView = view.findViewById(R.id.text_field)
        val cancelButton: Button = view.findViewById(R.id.button_cancel)

        // Retrieve data from arguments
        val result = arguments?.getString("result", "")
        resultTextView.text = result

        cancelButton.setOnClickListener {
            // Implement the cancel button action
            // For example, pop the fragment from the back stack
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
