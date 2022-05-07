package com.feelingsdiary.feelingsdiaryscripts

class Question(// Returns the question
    // Store question and the attached weights
    private var questionID: Int, // Returns the question's weights for scoring
    private var moduleNums: IntArray
) {

    init {
        this.questionID = questionID
        this.moduleNums = moduleNums
    }

    fun getQuestionID(): Int {
        return questionID
    }

    fun getModuleNums(): IntArray {
        return moduleNums
    }
}