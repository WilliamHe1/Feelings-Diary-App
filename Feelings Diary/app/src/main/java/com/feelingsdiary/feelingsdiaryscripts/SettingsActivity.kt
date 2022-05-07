package com.feelingsdiary.feelingsdiaryscripts
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.feelingssdiary.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedpreferences: SharedPreferences
    var nameInput = ""
    var schoolInput = ""

    lateinit var nameField: EditText
    lateinit var schoolField: EditText
    lateinit var saveButton: Button
    lateinit var backButton: Button


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        nameField = findViewById(R.id.nameInput)
        schoolField = findViewById(R.id.schoolInput)

        sharedpreferences = getSharedPreferences(settingPref, Context.MODE_PRIVATE)

        saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {

            if(nameField.text.toString() == "" || schoolField.text.toString() == "") {

                Toast.makeText(
                    this,
                    "Your name and/or school field need to be filled out.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                Toast.makeText(
                    this,
                    "Your settings have been saved!",
                    Toast.LENGTH_LONG
                ).show()
            }
            Save()
        }

        backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    fun Save() {

        val editor = sharedpreferences.edit()
        val name = nameField.text.toString()
        val school = schoolField.text.toString()

        editor.putString(NameKey, name)
        editor.putString(SchoolNameKey, school)
        editor.apply()

    }

    companion object {
        val settingPref = "mypref"
        val NameKey = "nameKey"
        val SchoolNameKey = "schoolkey"
    }
}