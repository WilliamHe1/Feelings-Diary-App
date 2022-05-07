package com.feelingsdiary.feelingsdiaryscripts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.feelingssdiary.R

class MainActivity : AppCompatActivity() {
    private var user_name = ""
    private var school_name = ""
    lateinit var welcome_msg: TextView
    lateinit var quote_display: TextView
    private var quizScore = "None"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcome_msg = findViewById(R.id.welcomeMsg)
        quote_display = findViewById(R.id.inspirationalQuote)

        //click dbt_button for user to get dbt activity.
        val dbt_button = findViewById<Button>(R.id.dbtActivityButton)
        dbt_button.setOnClickListener {
            if(user_name == "" || school_name == ""){
                pleaseEnterInfo()
            }
            else if(quizScore in listOf("M", "DT", "ER", "IE")){

                val intent = Intent(this, DBTActivity::class.java)
                intent.putExtra("quizScore", quizScore )
                startActivity(intent)

            }
            else{
                Toast.makeText(
                    this,
                    "Please complete the Let Us Know How You Feel Quiz first.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //directs user to take quiz.
        val quiz_button = findViewById<Button>(R.id.quizButton)
        quiz_button.setOnClickListener {
            if(user_name == "" || school_name == ""){
                pleaseEnterInfo()
            }
            else{
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)

            }
        }

        //goes to settings so user can enter name and school.
        val settings_button = findViewById<Button>(R.id.settingsButton)
        settings_button.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)


        }

        //directs user to the diary.
        val log_button = findViewById<Button>(R.id.logButton)
        log_button.setOnClickListener {
            if(user_name == "" || school_name == ""){
                pleaseEnterInfo()
            }
            else{
                val intent = Intent(this, DiaryMainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.i(TAG, "Entered the onResume() method")

        //array containing the random quotes.
        val quoteArray = arrayOf(
            "What consumes your Mind, controls your life. - Mel Robbins",
            "Tears are words that need to be written. - Paulo Coelho",
            "Action is the antidote to despair. - Joan Baez",
            "Difficult roads lead to beautiful destinations. - Zig Ziglar")
        val quoteArrayLen = quoteArray?.size
        val ranNum= (0 until quoteArrayLen!!).random()

        //generates random inspirational quote for the main page.
        quote_display.text = quoteArray?.get(ranNum)

        //"settingPref" stores information regarding the user's
        //name, school, and quiz score.
        val mPrefs = getSharedPreferences(settingPref, 0)
        if(mPrefs.contains(NameKey) && mPrefs.contains(SchoolNameKey)){
            user_name = mPrefs.getString(NameKey, "").toString()
            school_name = mPrefs.getString(SchoolNameKey, "").toString()
        }
        if(mPrefs.contains(quizScoreKey)){
            quizScore = mPrefs.getString(quizScoreKey, "").toString()
            Log.i(TAG, "QUIZ SCORE IS:     " + quizScore)
        }

        if(user_name == "" || school_name == ""){
            welcome_msg.text = "Hello! \nPlease enter your name and school in the settings menu."
        }
        else{
            welcome_msg.text = "Hello " + user_name + ". We hope that your studies at " + school_name +
                    " are going well."
        }
    }

    //if user does not have their name and/or school put in, toast gets shown
    //saying that the user cannot access the features of the app.
    private fun pleaseEnterInfo(){
        Toast.makeText(
            this,
            "Please enter your name and school in the settings menu to access.",
            Toast.LENGTH_LONG
        ).show()
    }


    companion object {
        // String for LogCat documentation
        private val TAG = "FeelingsDiaryMain"
        val settingPref = "mypref"
        val NameKey = "nameKey"
        val SchoolNameKey = "schoolkey"
        val quizScoreKey = "quizKey"
    }
}