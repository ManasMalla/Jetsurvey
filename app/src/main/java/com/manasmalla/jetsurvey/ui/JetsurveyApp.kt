package com.manasmalla.jetsurvey.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manasmalla.jetsurvey.ui.onboarding.SignInScreen
import com.manasmalla.jetsurvey.ui.onboarding.WelcomeScreen
import com.manasmalla.jetsurvey.ui.survey.SurveyScaffold

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_IN_ROUTE = "signin/{email}"
    const val SURVEY_ROUTE = "survey"
    const val SURVEY_RESULT_ROUTE = "result"
}

@Composable
fun JetsurveyApp(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Destinations.WELCOME_ROUTE) {
        composable(Destinations.WELCOME_ROUTE) {
            WelcomeScreen(onNavigateToSignIn = {
                navController.navigate("signin/$it")
            }, onNavigateToSurvey = {
                navController.navigate(Destinations.SURVEY_ROUTE)
            })
        }
        composable(Destinations.SIGN_IN_ROUTE) {
            val startingEmail = it.arguments?.getString("email")
            SignInScreen(startingEmail = startingEmail, onNavigateBack = {
                navController.navigateUp()
            }, onNavigateToSurvey = {
                navController.navigate(Destinations.SURVEY_ROUTE)
            })
        }
        composable(Destinations.SURVEY_ROUTE) {
            SurveyScaffold(onNavigateUp = {
                navController.navigateUp()
            }, onNavigateToResults = {
                navController.navigate(Destinations.SURVEY_RESULT_ROUTE)
            })
        }
        composable(Destinations.SURVEY_RESULT_ROUTE){
            SurveyResultScreen {
                navController.popBackStack(Destinations.WELCOME_ROUTE, false)
            }
        }
    }
}