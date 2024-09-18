package com.example.gymsaround.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymsaround.gyms.presentation.details.GymsDetailsScreen
import com.example.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.example.gymsaround.gyms.presentation.gymslist.GymsViewModel
import com.example.gymsaround.ui.theme.GymsAroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            val viewModel: GymsViewModel = hiltViewModel()
            GymsScreen(
                state = viewModel.state.value,
                onItemClick = { gymId ->
                    navController.navigate("gyms/$gymId")
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
            })
        ) { backStackEntry ->
            val gymId = backStackEntry.arguments?.getInt("gym_id") ?: 0
            GymsDetailsScreen(gymId = gymId)
        }
    }

}
