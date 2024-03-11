package com.example.lab2android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2android.R
import com.example.lab2android.fragments.FirstFragment
import com.example.lab2android.fragments.SecondFragment

class MainActivity : AppCompatActivity() {


    private lateinit var firstFragment: FirstFragment
    private lateinit var secondFragment: SecondFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        firstFragment = FirstFragment()

        firstFragment.setOnClickListener { result ->
            onOkButtonClick(result)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, firstFragment)
            .commit()


    }


    private fun onOkButtonClick(result: String) {
        secondFragment = SecondFragment.newInstance(result)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, secondFragment)
            .addToBackStack(null)
            .commit()

        secondFragment.setOnClickListener { clean ->
            if (clean)
                firstFragment.clean()

        }
    }


}

