package com.eldroid.bottomnavigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var checkBoxSports: CheckBox
    private lateinit var checkBoxMusic: CheckBox
    private lateinit var checkBoxArt: CheckBox
    private lateinit var buttonSave: Button

    private val sharedPrefsFile = "profilePrefs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        checkBoxSports = view.findViewById(R.id.checkBoxSports)
        checkBoxMusic = view.findViewById(R.id.checkBoxMusic)
        checkBoxArt = view.findViewById(R.id.checkBoxArt)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Load saved profile data
        loadProfileInfo()

        // Set click listener for save button
        buttonSave.setOnClickListener {
            saveProfileInfo()
        }

        return view
    }

    private fun saveProfileInfo() {
        // Get the name and email input
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()

        // Get selected gender
        val selectedGenderId = radioGroupGender.checkedRadioButtonId
        val selectedGender = when (selectedGenderId) {
            R.id.radioMale -> "Male"
            R.id.radioFemale -> "Female"
            R.id.radioOther -> "Other"
            else -> "Not Specified"
        }

        // Get interests from checkboxes
        val interests = mutableListOf<String>()
        if (checkBoxSports.isChecked) interests.add("Sports")
        if (checkBoxMusic.isChecked) interests.add("Music")
        if (checkBoxArt.isChecked) interests.add("Art")

        // Save to SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("gender", selectedGender)
        editor?.putBoolean("interest_sports", checkBoxSports.isChecked)
        editor?.putBoolean("interest_music", checkBoxMusic.isChecked)
        editor?.putBoolean("interest_art", checkBoxArt.isChecked)
        editor?.apply()

        // Confirm save with a Toast
        Toast.makeText(activity, "Profile Saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileInfo() {
        val sharedPreferences = activity?.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE)

        // Load saved values from SharedPreferences
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        val gender = sharedPreferences?.getString("gender", "Not Specified")
        val isSportsChecked = sharedPreferences?.getBoolean("interest_sports", false)
        val isMusicChecked = sharedPreferences?.getBoolean("interest_music", false)
        val isArtChecked = sharedPreferences?.getBoolean("interest_art", false)

        // Set the loaded values to the views
        editTextName.setText(name)
        editTextEmail.setText(email)

        when (gender) {
            "Male" -> radioGroupGender.check(R.id.radioMale)
            "Female" -> radioGroupGender.check(R.id.radioFemale)
            "Other" -> radioGroupGender.check(R.id.radioOther)
            else -> radioGroupGender.clearCheck()
        }

        checkBoxSports.isChecked = isSportsChecked == true
        checkBoxMusic.isChecked = isMusicChecked == true
        checkBoxArt.isChecked = isArtChecked == true
    }
}
