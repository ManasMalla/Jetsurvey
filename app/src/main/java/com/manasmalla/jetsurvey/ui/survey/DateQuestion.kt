package com.manasmalla.jetsurvey.ui.survey

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme
import com.manasmalla.jetsurvey.ui.theme.slightlyDeemphasizedAlpha

@Composable
fun DateQuestion(date: String, showDatePicker: () -> Unit = {}) {


    OutlinedButton(
        onClick = showDatePicker,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface.copy(
                slightlyDeemphasizedAlpha
            )
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(0.12f)),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = date,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
        )
        Icon(imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "Expand")
    }

}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DateQuestionPreview() {
    JetsurveyTheme {
        DateQuestion("Sat, Mar 11")
    }
}