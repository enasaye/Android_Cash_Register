package com.example.cashregister

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cashregister.UILayer.Screens.HistoryDetailsScreen
import com.example.cashregister.UILayer.Screens.HistoryListScreen
import com.example.cashregister.UILayer.Screens.HomeScreen
import com.example.cashregister.UILayer.Screens.ManagerScreen
import com.example.cashregister.UILayer.Screens.RestockScreen

@Composable
fun CashRegisterNavHost(
    navController: NavHostController,
    viewModel: CashRegisterViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        // 1. HOME SCREEN
        composable(route = "home") {
            HomeScreen(
                viewModel = viewModel,
                onManagerClicked = { navController.navigate("manager") }
            )
        }

        // 2. MANAGER SCREEN
        composable(route = "manager") {
            ManagerScreen(
                onHistoryClicked = { navController.navigate("history") },
                onRestockClicked = { navController.navigate("restock") }
            )
        }

        // 3. RESTOCK SCREEN
        composable(route = "restock") {
            RestockScreen(
                viewModel = viewModel
            )
        }

        // 4. HISTORY SCREEN
        composable(route = "history") {
            HistoryListScreen( //
                viewModel = viewModel,
                onPurchaseClicked = { purchaseId ->
                    navController.navigate("history_detail/$purchaseId")
                }
            )
        }

        // 5. HISTORY DETAIL SCREEN (Takes an argument in the route)
        composable(
            route = "history_detail/{purchaseId}",
            arguments = listOf(navArgument("purchaseId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the ID from the route
            val purchaseId = backStackEntry.arguments?.getInt("purchaseId")

            // Look up the actual purchase from the ViewModel's state
            val purchases by viewModel.repository.purchases.collectAsState()
            val selectedPurchase = purchases.find { it.id == purchaseId }

            if (selectedPurchase != null) {
                HistoryDetailsScreen(purchase = selectedPurchase) // <--- Changed this
            }
        }
    }
}