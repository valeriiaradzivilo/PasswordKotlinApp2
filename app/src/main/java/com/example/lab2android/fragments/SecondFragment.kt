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

    companion object {
        fun newInstance(data: String): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putString("data", data)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var onCancelClick: (clean: Boolean) -> Unit?
    fun setOnClickListener(listener: (Boolean) -> Unit) {
        onCancelClick = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val resultTextView: TextView = view.findViewById(R.id.text_field)
        val cancelButton: Button = view.findViewById(R.id.button_cancel)


        val result = arguments?.getString("data", "")
        resultTextView.text = result

        cancelButton.setOnClickListener {
            onCancelClick(true)
            requireActivity().supportFragmentManager.popBackStack()

        }
    }
}
