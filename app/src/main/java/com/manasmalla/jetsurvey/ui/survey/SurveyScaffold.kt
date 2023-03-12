package com.manasmalla.jetsurvey.ui.survey

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manasmalla.jetsurvey.data.Options
import com.manasmalla.jetsurvey.ui.survey.util.SurveyBottomBar
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme
import com.manasmalla.jetsurvey.ui.theme.slightlyDeemphasizedAlpha
import com.manasmalla.jetsurvey.ui.theme.stronglyDeemphasizedAlpha

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SurveyScaffold(
    modifier: Modifier = Modifier,
    onNavigateUp: ()->Unit = {},
    onNavigateToResults: ()->Unit = {}
) {
    val surveyViewModel: SurveyViewModel = viewModel()
    val progress = surveyViewModel.progress
    val question = surveyViewModel.question
    Scaffold(topBar = {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$progress of 5",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 48.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.labelLarge
                )
                IconButton(onClick = onNavigateUp) {
                    Icon(Icons.Rounded.Close, contentDescription = null)
                }
            }
            LinearProgressIndicator(
                progress = 0.2f.times(progress),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }, bottomBar = {
        SurveyBottomBar(
            progress = progress,
            isNextEnabled = surveyViewModel.isNextEnabled,
            onPreviousPressed = surveyViewModel::previousQuestion,
            onNextPressed =
                if(surveyViewModel.progress!= 5)
                    surveyViewModel::nextQuestion
                else onNavigateToResults

        )
    }, modifier = modifier.statusBarsPadding()) {
        AnimatedContent(targetState = question) { surveyQuestion ->
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    surveyQuestion.question,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(20.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = slightlyDeemphasizedAlpha),
                )
                if (surveyQuestion.description != "")
                    Text(
                        text = surveyQuestion.description,
                        modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
                        style = MaterialTheme.typography.bodySmall,

                        color = MaterialTheme.colorScheme.onSurface
                            .copy(alpha = stronglyDeemphasizedAlpha),
                    )

                when (val options = surveyQuestion.options) {
                    Options.DateChoice -> {

                        val fragmentManager =
                            LocalContext.current.findActivity().supportFragmentManager
                        DateQuestion(
                            date = surveyViewModel.formattedDate, showDatePicker = {
                                surveyViewModel.showDatePicker(fragmentManager = fragmentManager)
                            }
                        )

                    }

                    Options.ImageChoice -> {
                        FileQuestion(
                            modifier = Modifier.padding(16.dp),
                            imageUri = surveyViewModel.selfie,
                            onImageSelected = surveyViewModel::onImageSelected)
                    }

                    is Options.MultipleChoice -> {
                        MultipleOptionsSection(
                            options = options,
                            selectedOptions = surveyViewModel.freeTimeOptions,
                            onOptionSelected = surveyViewModel::updateMultipleOptionsAnswer
                        )
                    }

                    is Options.SingleChoice -> {
                        SingleOptionsSection(
                            selected = surveyViewModel.composeCharacter,
                            options = options,
                            onOptionSelected = surveyViewModel::updateComposeCharacter)
                    }

                    is Options.SliderChoice -> {
                        SliderChoice(
                            options = options,
                            modifier = Modifier.padding(16.dp),
                            value = surveyViewModel.selfieFeeling ?: 0.5f,
                            onValueChange = surveyViewModel::onSelfieFeelingChange)

                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurveyScaffoldPreview() {
    JetsurveyTheme {
        SurveyScaffold()
    }
}