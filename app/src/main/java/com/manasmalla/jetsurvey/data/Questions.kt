package com.manasmalla.jetsurvey.data

import com.manasmalla.jetsurvey.R

val questions = listOf(
    SurveyQuestion(
        question = "In my free time I like to ...",
        description = "Select all that apply.",
        Options.MultipleChoice(
            options = listOf(
                "Read",
                "Work out",
                "Draw",
                "Play video games",
                "Dance",
                "Watch Movies"
            )
        )
    ),
    SurveyQuestion(
        question = "Pick a Compose comic character",
        description = "Select one.",
        options = Options.SingleChoice(options = mapOf(
            "Spark" to R.drawable.spark,
            "Lenz" to R.drawable.lenz,
            "Bug of Chaos" to R.drawable.bug_of_chaos,
            "Frag" to R.drawable.frag
        ))
    ),
    SurveyQuestion(
        question = "When was the last time you ordered takeaway because you couldn't be bothered to cook?",
        description = "Select date.",
        options = Options.DateChoice
    ),
    SurveyQuestion(
        question = "How do you feel about selfies 🤳?",
        options = Options.SliderChoice(sliderOptions = listOf(
            "Strongly\nDislike", "Neutral", "Strongly\nLike"
        ))
    ),
    SurveyQuestion(question = "Show off your selfie skills!", options = Options.ImageChoice)
)