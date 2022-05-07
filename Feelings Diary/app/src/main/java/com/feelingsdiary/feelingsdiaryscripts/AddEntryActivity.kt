package com.feelingsdiary.feelingsdiaryscripts

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.SharedPreferences
import android.util.Log
import com.example.feelingssdiary.R


class AddEntryActivity : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        var entryTitle = findViewById<EditText>(R.id.userinputtitle)
        var entry = findViewById<EditText>(R.id.userinputentry)
        var savebtn = findViewById<Button>(R.id.addentrybtn)


        var sharedPreferences: SharedPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        savebtn.setOnClickListener{
            if (entryTitle.text.isEmpty() or entry.text.isEmpty()) {
                Toast.makeText(applicationContext, "Both fields required", Toast.LENGTH_LONG).show()
            }
            else {
                val createdTime : Long = System.currentTimeMillis()
                editor.putString(createdTime.toString(), entryTitle.text.toString() + ", " + entry.text.toString())
                editor.apply()
                Toast.makeText(applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
                finish() //// ##### GO BACK TO HOME FROM HERE #######///////
            }
        }

    }
}