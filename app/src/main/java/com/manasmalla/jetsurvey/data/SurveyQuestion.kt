package com.manasmalla.jetsurvey.data

sealed class Options{
    data class MultipleChoice(val options: List<String>): Options()
    data class SingleChoice(val options: Map<String, Int>): Options()
    object DateChoice: Options()
    data class SliderChoice(val sliderOptions: List<String>): Options()
    object ImageChoice: Options()
}

data class SurveyQuestion(
    val question: String, val description:String = "", val options: Options
)
