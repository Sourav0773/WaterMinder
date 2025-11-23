package com.example.waterminder.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.waterminder.db.dao.UserDAO
import com.example.waterminder.ui.screens.*

@Composable
fun NavGraph(
    startDestination: String = "splash",
    userDao: UserDAO
) {

    val navController = rememberNavController()
    val duration = 300 // Animation duration

    // Helper functions within the scope of NavGraph

    // New screen enters from the right (slides left), scaling in slightly.
    fun AnimatedContentTransitionScope<*>.enterTransition() = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = duration)
    ) + scaleIn(
        initialScale = 1.05f, // Starts slightly larger than normal
        animationSpec = tween(durationMillis = duration)
    )

    // Old screen exits to the left (slides left), scaling out slightly.
    fun AnimatedContentTransitionScope<*>.exitTransition() = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = duration)
    ) + scaleOut(
        targetScale = 0.95f, // Shrinks slightly
        animationSpec = tween(durationMillis = duration)
    ) + fadeOut(animationSpec = tween(durationMillis = duration))

    // When pressing 'back', the new screen enters from the left (slides right), scaling in.
    fun AnimatedContentTransitionScope<*>.popEnterTransition() = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = duration)
    ) + scaleIn(
        initialScale = 0.95f, // Starts slightly smaller
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(animationSpec = tween(durationMillis = duration))

    // When pressing 'back', the old screen exits to the right (slides right), scaling out.
    fun AnimatedContentTransitionScope<*>.popExitTransition() = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = duration)
    ) + scaleOut(
        targetScale = 1.05f, // Scales slightly larger as it leaves
        animationSpec = tween(durationMillis = duration)
    )


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = "splash",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { SplashScreen(navController) }

        composable(
            route = "signup",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { SignupScreen(navController) }

        composable(
            route = "login",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { LoginScreen(navController) }

        composable(
            route = "home",
            // Home might have slightly different transitions if it's the root level
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { HomeScreen(
            navController = navController,
            userDao = userDao
        ) }
    }
}
