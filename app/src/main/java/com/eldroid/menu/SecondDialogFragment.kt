package com.eldroid.menu

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SecondDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the dialog layout
        val view = inflater.inflate(R.layout.second_fragment_dialog, container, false)

        // Handle positive button click
        val positiveButton = view.findViewById<Button>(R.id.positiveButton)
        positiveButton.setOnClickListener {
            // Navigate to FirstFragment
            (activity as MainActivity).loadFirstFragment()
            dismiss()  // Close the dialog
        }

        // Handle negative button click
        val negativeButton = view.findViewById<Button>(R.id.negativeButton)
        negativeButton.setOnClickListener {
            // Exit the app
            activity?.finishAffinity()  // Close the app
        }

        return view
    }
}

