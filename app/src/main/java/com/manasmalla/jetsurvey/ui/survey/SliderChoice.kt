package com.manasmalla.jetsurvey.ui.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.manasmalla.jetsurvey.data.Options
import com.manasmalla.jetsurvey.data.questions
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme

@Composable
fun SliderChoice(value: Float = 0.5f, onValueChange: (Float)->Unit = {}, options: Options.SliderChoice, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Slider(value = value, onValueChange = onValueChange, steps = 3)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            options.sliderOptions.forEach {
                Text(it, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SliderChoicePreview() {
    JetsurveyTheme {
        SliderChoice(options = questions[3].options as Options.SliderChoice)
    }
}