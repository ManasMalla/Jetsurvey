package com.manasmalla.jetsurvey.ui.survey

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.manasmalla.jetsurvey.data.Options
import com.manasmalla.jetsurvey.data.questions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SurveyViewModel : ViewModel() {

    var progress by mutableStateOf(1)
        private set
    val question get() = questions[progress - 1]

    var isNextEnabled by mutableStateOf(false)
        private set

    val freeTimeOptions = mutableStateListOf<String>()
    var composeCharacter by mutableStateOf("")
    var selfieFeeling by mutableStateOf<Float?>(null)
    var selfie: Uri? by mutableStateOf(null)
    private var date: Long? by mutableStateOf(null)
    val formattedDate: String
        get() = SimpleDateFormat(
            "EEE, MMM dd",
            Locale.getDefault()
        ).format(Date(date ?: Calendar.getInstance().time.time))

    fun showDatePicker(fragmentManager: FragmentManager) {
        val picker =
            MaterialDatePicker.Builder.datePicker().setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()).build()
            ).setSelection(date).build()
        picker.show(fragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            date = it
            isNextEnabled = checkIfNextEnabled()
        }
    }

    private fun checkIfNextEnabled(): Boolean {
        return when (question.options) {
            Options.DateChoice -> date != null
            Options.ImageChoice -> selfie != null
            is Options.MultipleChoice -> freeTimeOptions.isNotEmpty()
            is Options.SingleChoice -> composeCharacter != ""
            is Options.SliderChoice -> selfieFeeling != null
        }
    }

    fun nextQuestion() {
        progress += 1
        isNextEnabled = checkIfNextEnabled()
    }

    fun previousQuestion() {
        progress--
    }

    fun updateMultipleOptionsAnswer() {
        isNextEnabled = checkIfNextEnabled()
    }

    fun updateComposeCharacter(character: String) {
        composeCharacter = character
        isNextEnabled = checkIfNextEnabled()
    }

    fun onSelfieFeelingChange(newFeeling: Float) {
        selfieFeeling = newFeeling
        isNextEnabled = checkIfNextEnabled()
    }

    fun onImageSelected(uri: Uri) {
        selfie = uri
        isNextEnabled = checkIfNextEnabled()
    }

}