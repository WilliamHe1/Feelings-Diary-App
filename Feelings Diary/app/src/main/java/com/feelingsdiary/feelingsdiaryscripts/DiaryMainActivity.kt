package com.feelingsdiary.feelingsdiaryscripts

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feelingssdiary.R

class DiaryMainActivity : AppCompatActivity() {
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var ouradapter: Adapter

    fun openActivityForResult() {
        val intent = Intent(this, AddEntryActivity::class.java)
        launcher.launch(intent)
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        ouradapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_main)

        var addEntryBtn = findViewById<Button>(R.id.addButton)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("diary", Context.MODE_PRIVATE)
        var preflistener = OnSharedPreferenceChangeListener { prefs, key ->
            ouradapter.notifyDataSetChanged()
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(preflistener)

        linearLayoutManager = LinearLayoutManager(this)
        val ourrecycler = findViewById<RecyclerView>(R.id.recyclerview)
        ouradapter = Adapter(applicationContext,sharedPreferences)
        ourrecycler.layoutManager = linearLayoutManager
        ourrecycler.adapter = ouradapter
        ViewCompat.setNestedScrollingEnabled(ourrecycler, false)

        addEntryBtn.setOnClickListener{
            openActivityForResult()
            ouradapter.notifyDataSetChanged()
        }

    }
}