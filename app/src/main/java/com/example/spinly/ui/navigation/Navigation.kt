package com.example.spinly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spinly.ui.screens.JoinRoomScreen
import com.example.spinly.ui.screens.LoginScreen
import com.example.spinly.ui.screens.MainRoomScreen
import com.example.spinly.ui.screens.ProfileScreen

object Routes {
    const val LOGIN     = "login"
    const val JOIN_ROOM = "join_room"
    const val MAIN_ROOM = "main_room/{roomCode}"
    const val PROFILE   = "profile"

    fun mainRoom(code: String) = "main_room/$code"
}

@Composable
fun SpinAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController    = navController,
        startDestination = Routes.LOGIN,   // ← mulai dari Login
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.JOIN_ROOM) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.JOIN_ROOM) {
            JoinRoomScreen(
                onJoinSuccess = { roomCode ->
                    navController.navigate(Routes.mainRoom(roomCode)) {
                        popUpTo(Routes.JOIN_ROOM) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.MAIN_ROOM) { backStackEntry ->
            val roomCode = backStackEntry.arguments?.getString("roomCode") ?: ""
            MainRoomScreen(
                roomCode  = roomCode,
                onProfile = { navController.navigate(Routes.PROFILE) },
                onLeave   = {
                    navController.navigate(Routes.JOIN_ROOM) {
                        popUpTo(Routes.JOIN_ROOM) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack   = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}