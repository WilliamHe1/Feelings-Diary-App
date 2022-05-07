package com.feelingsdiary.feelingsdiaryscripts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feelingssdiary.R


class DBTActivity : AppCompatActivity(){

    private var quizScore = "M"
    private lateinit var dbtMessage: TextView
    private lateinit var beginButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbt)

        dbtMessage = findViewById(R.id.taskMsg)

        val mPrefs = getSharedPreferences(MainActivity.settingPref, 0)
        quizScore = mPrefs.getString(MainActivity.quizScoreKey, "").toString()
        Log.i(TAG, "QUIZ SCORE IS:     " + quizScore)

        backButton = findViewById(R.id.dbtBack)
        backButton.setOnClickListener {
            finish()
        }
        beginButton = findViewById<Button>(R.id.beginDBTButton)

        //Check the quiz score to determine the activity.
        beginButton.setOnClickListener{

            //direct user to write in the diary.
            if(quizScore == "ER"){
                //goto diary
                clearScore()
                val intent = Intent(this, DiaryMainActivity::class.java)
                startActivity(intent)
            }
            //directs user to use a timer for 20 min to do some meditation.
            else if(quizScore == "DT"){
                requestPermissions(arrayOf(TIMER_ACTION), TIMER_PERMISSION_REQUEST)
            }
            //directs user to contacts to talk to a friend.
            else if(quizScore == "IE"){

                requestPermissions(arrayOf(READ_P_NUMBERS, READ_P_STATE, READ_SMS),
                    CONTACT_PERMISSION_REQUEST
                )

            }
            //directs user to Youtube Page to learn more about mental health.
            else if(quizScore == "M"){

                clearScore()
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/c/theschooloflifetv/featured")
                    )
                )
            }
        }
    }
    public override fun onResume() {
        super.onResume()
        if(quizScore == "ER"){
            dbtMessage.text = getString(R.string.ER)
        }
        else if(quizScore == "DT"){
            dbtMessage.text = getString(R.string.DT)
        }
        else if(quizScore == "IE"){
            dbtMessage.text = getString(R.string.IE)
        }
        else if(quizScore == "M"){
            dbtMessage.text = getString(R.string.M)
        }
        else{
            dbtMessage.text =
                "Please complete the quiz so that we can " +
                        "recommend you a DBT Activity to do."
            beginButton!!.visibility = View.INVISIBLE
        }
    }

    //permission results to launch specific dbt activity.
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            TIMER_PERMISSION_REQUEST -> {

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    val clockIntent = Intent(AlarmClock.ACTION_SET_TIMER)
                    clockIntent.putExtra(AlarmClock.EXTRA_LENGTH, 1200)
                    clearScore()
                    startActivity(clockIntent)

                } else {
                    Toast.makeText(
                        this,
                        "CLOCK APP FAILED TO OPEN.",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.i(TAG, "CLOCK WONT OPEN")

                }

                return
            }
            CONTACT_PERMISSION_REQUEST -> {

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    val contactsIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                    clearScore()
                    startActivity(contactsIntent)

                } else {

                    Toast.makeText(
                        this,
                        "CONTACT APP FAILED TO OPENy.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }


        }
    }

    //Used to clear quiz score once the user begins the dbt activity (the user
    // will have to do another quiz to get another score).
    private fun clearScore() {
        val mPrefs = getSharedPreferences(MainActivity.settingPref, 0)
        val editor = mPrefs.edit()
        quizScore = "None"
        editor.putString(MainActivity.quizScoreKey, quizScore)
        editor.apply()
        Log.i("Feelings", "IN THE QUIZ  "
                + mPrefs.getString(MainActivity.quizScoreKey, "").toString())

    }

    companion object {
        // String for LogCat documentation
        private val TAG = "DBT Section"
        val TIMER_ACTION = "com.android.alarm.permission.SET_ALARM"
        val READ_P_STATE = Manifest.permission.READ_PHONE_STATE
        val READ_P_NUMBERS = Manifest.permission.READ_PHONE_NUMBERS
        val READ_SMS = Manifest.permission.READ_SMS
        val TIMER_PERMISSION_REQUEST = 2
        val CONTACT_PERMISSION_REQUEST = 3
    }
}