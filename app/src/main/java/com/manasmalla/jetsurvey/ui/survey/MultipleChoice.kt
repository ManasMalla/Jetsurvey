package com.manasmalla.jetsurvey.ui.survey

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manasmalla.jetsurvey.data.Options
import com.manasmalla.jetsurvey.data.questions
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme

@Composable
fun MultipleOptionsSection(
    options: Options.MultipleChoice,
    selectedOptions: MutableList<String>,
    onOptionSelected: () -> Unit = {}
) {
    options.options.forEach { option ->
        key(option) {
            CheckboxRow(title = option, selected = selectedOptions.contains(option), onOptionSelected = {
                if(selectedOptions.contains(option)) selectedOptions.remove(option) else selectedOptions.add(option)
                onOptionSelected()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxRow(title: String = "Checbox Title", selected: Boolean = false, onOptionSelected: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onOptionSelected),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Checkbox(
                checked = selected,
                modifier = Modifier.padding(8.dp),
                onCheckedChange = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultipleOptionListPreview() {
    JetsurveyTheme {

        val selectedItems = remember {
            mutableStateListOf<String>()
        }
        Column {
            MultipleOptionsSection(options = questions.first().options as Options.MultipleChoice, selectedOptions = selectedItems)
        }
    }
}
