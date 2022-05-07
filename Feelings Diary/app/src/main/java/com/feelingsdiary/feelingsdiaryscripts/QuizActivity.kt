package com.feelingsdiary.feelingsdiaryscripts

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.feelingssdiary.R

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    private var secondButton: Button? = null
    private var firstButton: Button? = null
    private var thirdButton: Button? = null
    private var fourthButton: Button? = null
    private var fifthButton: Button? = null
    private var exitButton: Button? = null
    private var nextButton: ImageButton? = null
    private var prevButton: ImageButton? = null
    private var Image: ImageView? = null
    private var questionText: TextView? = null
    private var currQuestion = 0

    var quizScore = "None"

    // Counters for the module weights
    var M_counter = 0
    var IE_counter = 0
    var DT_counter = 0
    var ER_counter = 0
    private val questionsList = arrayOf( // Array of questions and their weights
        Question(R.string.q1, intArrayOf(1, 1, 3, 3)),
        Question(R.string.q2, intArrayOf(1, 1, 2, 2)),
        Question(R.string.q3, intArrayOf(1, 1, 3, 3)),
        Question(R.string.q4, intArrayOf(1, 1, 3, 3)),
        Question(R.string.q5, intArrayOf(1, 1, 2, 2)),
        Question(R.string.q6, intArrayOf(1, 2, 1, 1)),
        Question(R.string.q7, intArrayOf(1, 3, 2, 1)),
        Question(R.string.q8, intArrayOf(2, 2, 1, 2)),
        Question(R.string.q9, intArrayOf(2, 3, 1, 2)),
        Question(R.string.q10, intArrayOf(2, 2, 1, 1)),
        Question(R.string.q11, intArrayOf(2, 2, 2, 1)),
        Question(R.string.q12, intArrayOf(3, 2, 1, 1)),
        Question(R.string.q13, intArrayOf(3, 2, 1, 1)),
        Question(R.string.q14, intArrayOf(3, 1, 1, 1))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        // Setting up buttons with their ids
        secondButton = findViewById(R.id.second_button)
        firstButton = findViewById(R.id.first_button)
        thirdButton = findViewById(R.id.third_button)
        fourthButton = findViewById(R.id.fourth_button)
        fifthButton = findViewById(R.id.fifth_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)

        exitButton = findViewById(R.id.exitQuizButton)
        exitButton?.visibility = View.INVISIBLE

        // Create click listeners for button
        questionText = findViewById(R.id.answer_text_view)

        Image = findViewById(R.id.imagePlace)
        Image?.setImageResource(R.drawable.p1)

        secondButton?.setOnClickListener(this)
        firstButton?.setOnClickListener(this)
        thirdButton?.setOnClickListener(this)
        fourthButton?.setOnClickListener(this)
        fifthButton?.setOnClickListener(this)
        nextButton?.setOnClickListener(this)
        prevButton?.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(M: View) {
        // When a button is clicked, either add to the totals or detect the end of questions
        when (M.id) {
            R.id.first_button -> updateWeights(1)
            R.id.second_button -> updateWeights(2)
            R.id.third_button -> updateWeights(3)
            R.id.fourth_button -> updateWeights(4)
            R.id.exitQuizButton -> exitQuiz()
            R.id.fifth_button -> updateWeights(5)
            R.id.next_button ->                 // Detects if there are any more questions
                if (currQuestion < 15) {
                    currQuestion += 1
                    // If no more questions, make buttons invisible and display
                    if (currQuestion == 14) {
                        nextButton!!.visibility = View.INVISIBLE
                        prevButton!!.visibility = View.INVISIBLE
                        firstButton!!.visibility = View.INVISIBLE
                        secondButton!!.visibility = View.INVISIBLE
                        thirdButton!!.visibility = View.INVISIBLE
                        fourthButton!!.visibility = View.INVISIBLE
                        fifthButton!!.visibility = View.INVISIBLE

                        exitButton!!.visibility= View.VISIBLE

                        // Good job picture indicating end of quiz.
                        Image!!.setImageResource(R.drawable.goodjob)

                        // Determine which module is best
                        // and have the quiz score set.
                        if (maxHelper(M_counter, IE_counter, DT_counter, ER_counter) == M_counter) {
                            questionText!!.text = "Great work! The module you should work on is Mindfulness."
                            quizScore = "M"
                        } else if (maxHelper(
                                M_counter,
                                IE_counter,
                                DT_counter,
                                ER_counter
                            ) == IE_counter
                        ) {
                            questionText!!.text =
                                "Great work! The module you should work on is Interpersonal Effectiveness."
                            quizScore = "IE"
                        } else if (maxHelper(
                                M_counter,
                                IE_counter,
                                DT_counter,
                                ER_counter
                            ) == DT_counter
                        ) {
                            questionText!!.text =
                                "Great work! The module you should work on is Distress Tolerance."
                            quizScore = "DT"
                        } else {
                            questionText!!.text =
                                "Great work! The module you should work on is Emotion Regulation."
                            quizScore = "ER"
                        }

                        exitButton!!.setOnClickListener(this)
                    } else {
                        updateScreen()
                    }
                }
            R.id.prev_button -> if (currQuestion > 0) {
                currQuestion = (currQuestion - 1) % questionsList.size
                updateScreen()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun updateScreen() {
        Log.d("Current", "onClick: $currQuestion")
        // Updates the pictures and question
        questionText?.setText(questionsList[currQuestion].getQuestionID())

        when (currQuestion % 3) {
            0 -> Image!!.setImageResource(R.drawable.p1)
            1 -> Image!!.setImageResource(R.drawable.p2)
            2 -> Image!!.setImageResource(R.drawable.p3)
        }
    }

    //leaves the quiz and saves the quiz score
    private fun exitQuiz() {
        val mPrefs = getSharedPreferences(settingPref, 0)
        val editor = mPrefs.edit()
        editor.putString(quizScoreKey, quizScore)
        editor.apply()
        Log.i("Feelings", "IN THE QUIZ  " + mPrefs.getString(quizScoreKey, "").toString())
        finish()

    }

    private fun updateWeights(slider: Int) {
        // Adds the weights times selection out of 5, and adds to counters
        val moduleWeights: IntArray = questionsList[currQuestion].getModuleNums()
        M_counter += moduleWeights[0] * slider
        IE_counter += moduleWeights[1] * slider
        DT_counter += moduleWeights[2] * slider
        ER_counter += moduleWeights[3] * slider
    }

    companion object {
        // Helper method to find the max of 4 ints
        fun maxHelper(a: Int, b: Int, c: Int, d: Int): Int {
            var maximum = a
            if (b > maximum) maximum = b
            if (c > maximum) maximum = c
            if (d > maximum) maximum = d
            return maximum
        }
            // String for LogCat documentation
            private val TAG = "FeelingsDiaryMain"
            val settingPref = "mypref"
            val NameKey = "nameKey"
            val SchoolNameKey = "schoolkey"
            val quizScoreKey = "quizKey"

    }
}