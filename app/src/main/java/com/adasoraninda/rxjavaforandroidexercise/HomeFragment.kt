package com.adasoraninda.rxjavaforandroidexercise

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_ex_1).setOnClickListener {
            navigate(Exercise1Fragment())
        }

        view.findViewById<Button>(R.id.button_ex_2).setOnClickListener {
            navigate(Exercise2Fragment())
        }

    }

    private fun navigate(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}