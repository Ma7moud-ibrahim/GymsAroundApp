package com.example.gymsaround.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.gymsaround.gyms.presentation.details.GymsDetailsScreen
import com.example.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.example.gymsaround.gyms.presentation.gymslist.GymsViewModel
import com.example.gymsaround.ui.theme.GymsAroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymsAroundTheme {
                GymsAroundApp()
            }
        }
    }
}

@Composable
fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            val viewModel = viewModel<GymsViewModel>()
            GymsScreen (
                state = viewModel.state.value,
                onItemClick = {navController.navigate("gyms/$id")
                },
                onFavouriteClick = { id, oldValue ->
                    viewModel.toggleFavouriteState(id, oldValue)
                }
            )

        }
        composable(
            route = "gyms/{gym_id}",
            arguments = listOf(navArgument("gym_id") {
                type = NavType.IntType
            },
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "https://www.gymsaround.com/details/{gym_id}"
            }
            )
        ) {
            GymsDetailsScreen()
        }
    }
}
