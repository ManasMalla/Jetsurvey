package com.manasmalla.jetsurvey.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manasmalla.jetsurvey.R
import com.manasmalla.jetsurvey.ui.survey.util.ErrorSnackbar
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    startingEmail: String? = null,
    onNavigateBack: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {}
) {
    var email by remember {
        mutableStateOf(startingEmail ?: "")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val isFormStateValid by remember {
        derivedStateOf { password != "" && email != "" }
    }

    val scope = rememberCoroutineScope()
    val snackbarErrorText = stringResource(id = R.string.feature_not_available)
    val snackbarActionLabel = stringResource(id = R.string.dismiss)
    val snackbarHost = remember { SnackbarHostState() }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Sign In")
        }, navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.Rounded.ChevronLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        })
    }, snackbarHost = {
        ErrorSnackbar(snackbarHostState = snackbarHost, onDismiss = {
            snackbarHost.currentSnackbarData?.dismiss()
        })
    }, containerColor = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { userEmail ->
                    email = userEmail
                },
                placeholder = {
                    Text("Email")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                shape = MaterialTheme.shapes.small
            )
            OutlinedTextField(value = password,
                shape = MaterialTheme.shapes.small,
                onValueChange = { userPassword ->
                    password = userPassword
                },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    // Keyboard Actions
                }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                placeholder = {
                    Text("Password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (!isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                            contentDescription = if (!isPasswordVisible) "Hide Password" else "View Password"
                        )
                    }
                })
            Button(
                onClick = {
                    onNavigateToSurvey()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp), enabled = isFormStateValid
            ) {
                Text("Sign in")
            }
            TextButton(
                onClick = {
                    scope.launch {
                        snackbarHost.showSnackbar(snackbarErrorText, snackbarActionLabel)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text("Forgot Password?")
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
}

@Preview
@Composable
fun SignInScreenPreview() {
    JetsurveyTheme {
        SignInScreen()
    }
}