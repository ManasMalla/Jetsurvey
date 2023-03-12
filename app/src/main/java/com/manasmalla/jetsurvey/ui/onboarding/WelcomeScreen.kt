package com.manasmalla.jetsurvey.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manasmalla.jetsurvey.R

@Preview
@Composable
fun Title(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_light),
            contentDescription = "Jetsurvey"
        )
        Text(
            text = "Better surveys with Jetpack Compose",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignInSection(modifier: Modifier = Modifier, onNavigateToSignIn: (String) -> Unit = {}, onNavigateToSurvey: () -> Unit = {}) {
    var email by remember {
        mutableStateOf("")
    }
    val isButtonEnabled by remember {
        derivedStateOf {
            email != ""
        }
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Sign in or create an account", modifier = Modifier.padding(vertical = 16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = {
                if (isButtonEnabled) {
                    onNavigateToSignIn(email)
                }
            }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            singleLine = true,
            shape = MaterialTheme.shapes.small
        )
        Button(
            onClick = {
                if (isButtonEnabled) {
                    onNavigateToSignIn(email)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp), enabled = isButtonEnabled
        ) {
            Text("Continue")
        }
        Text(text = "or")
        OutlinedButton(
            onClick = onNavigateToSurvey, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text("Sign in as a guest")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreen(onNavigateToSignIn: (String) -> Unit = {}, onNavigateToSurvey: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Title(
            Modifier
                .weight(1f)
                .wrapContentHeight(Alignment.CenterVertically)
        )
        SignInSection(onNavigateToSignIn = onNavigateToSignIn, onNavigateToSurvey = onNavigateToSurvey)
    }
}
