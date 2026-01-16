package com.example.fintechui.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fintechui.data.TradeLogStore
import com.example.fintechui.ui.screens.activity.ActivityScreen
import com.example.fintechui.ui.screens.asset.AssetDetailRoute
import com.example.fintechui.ui.screens.gallery.GalleryLink
import com.example.fintechui.ui.screens.gallery.UiKitGalleryScreen
import com.example.fintechui.ui.screens.portfolio.PortfolioHeroScreen
import com.example.fintechui.ui.screens.trade.ConfirmTradeRoute
import com.example.fintechui.ui.screens.trade.TradeSuccessScreen
import com.example.fintechui.ui.screens.trade.TradeTicketRoute
import com.example.fintechui.ui.screens.watchlist.WatchlistRoute

private data class BottomTab(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val tabs = listOf(
        BottomTab(
            route = Routes.PORTFOLIO,
            label = "Portfolio",
            icon = { Icon(Icons.Filled.PieChart, contentDescription = null) }
        ),
        BottomTab(
            route = Routes.WATCHLIST,
            label = "Watchlist",
            icon = { Icon(Icons.Filled.Star, contentDescription = null) }
        ),
        BottomTab(
            route = Routes.ACTIVITY,
            label = "Activity",
            icon = { Icon(Icons.Filled.List, contentDescription = null) }
        ),
        BottomTab(
            route = Routes.GALLERY,
            label = "Gallery",
            icon = { Icon(Icons.Filled.GridView, contentDescription = null) }
        )
    )

    val showBottomBar = currentDestination?.route in setOf(
        Routes.PORTFOLIO,
        Routes.WATCHLIST,
        Routes.ACTIVITY,
        Routes.GALLERY
    )

    Scaffold(
        floatingActionButton = {
            val onPortfolio = currentDestination?.route == Routes.PORTFOLIO
            if (onPortfolio) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.trade("BTC")) },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Trade")
                }
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        val selected = currentDestination
                            ?.hierarchy
                            ?.any { it.route == tab.route } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(tab.route) {
                                    // keeps one copy of each tab on the back stack
                                    popUpTo(Routes.PORTFOLIO) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = tab.icon,
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.GALLERY,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Routes.PORTFOLIO) {
                PortfolioHeroScreen(
                    onAssetClick = { symbol -> navController.navigate(Routes.asset(symbol)) },
                    onOpenWatchlist = { navController.navigate(Routes.WATCHLIST) }
                )
            }

            composable(Routes.WATCHLIST) {
                WatchlistRoute(
                    onAssetClick = { symbol -> navController.navigate(Routes.asset(symbol)) }
                )
            }

            composable(Routes.ACTIVITY) { backStackEntry ->
                ActivityScreen()
            }

            composable(
                route = Routes.ASSET,
                arguments = listOf(navArgument(Routes.ASSET_SYMBOL_ARG) { type = NavType.StringType })
            ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString(Routes.ASSET_SYMBOL_ARG) ?: "BTC"
                val name = when (symbol) {
                    "AAPL" -> "Apple Inc"
                    "TSLA" -> "Tesla"
                    "MSFT" -> "Microsoft"
                    "ETH" -> "Ethereum"
                    "NVDA" -> "NVIDIA"
                    else -> "Bitcoin"
                }

                AssetDetailRoute(
                    symbol = symbol,
                    name = name,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.TRADE,
                arguments = listOf(navArgument(Routes.TRADE_SYMBOL_ARG) { type = NavType.StringType })
            ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString(Routes.TRADE_SYMBOL_ARG) ?: "BTC"

                TradeTicketRoute(
                    symbol = symbol,
                    onBack = { navController.popBackStack() },
                    onReview = { side, amount ->
                        navController.navigate(Routes.confirm(symbol, side, amount))
                    }
                )
            }

            composable(
                route = Routes.CONFIRM,
                arguments = listOf(
                    navArgument(Routes.CONFIRM_SYMBOL_ARG) { type = NavType.StringType },
                    navArgument(Routes.CONFIRM_SIDE_ARG) { type = NavType.StringType },
                    navArgument(Routes.CONFIRM_AMOUNT_ARG) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString(Routes.CONFIRM_SYMBOL_ARG) ?: "BTC"
                val side = backStackEntry.arguments?.getString(Routes.CONFIRM_SIDE_ARG) ?: "BUY"
                val amount = backStackEntry.arguments?.getString(Routes.CONFIRM_AMOUNT_ARG) ?: "250"

                ConfirmTradeRoute(
                    symbol = symbol,
                    side = side,
                    amount = amount,
                    onBack = { navController.popBackStack() },
                    onDone = {
                        navController.navigate(Routes.success(symbol, side, amount)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = Routes.SUCCESS,
                arguments = listOf(
                    navArgument(Routes.SUCCESS_SYMBOL_ARG) { type = NavType.StringType },
                    navArgument(Routes.SUCCESS_SIDE_ARG) { type = NavType.StringType },
                    navArgument(Routes.SUCCESS_AMOUNT_ARG) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString(Routes.SUCCESS_SYMBOL_ARG) ?: "BTC"
                val side = backStackEntry.arguments?.getString(Routes.SUCCESS_SIDE_ARG) ?: "BUY"
                val amount = backStackEntry.arguments?.getString(Routes.SUCCESS_AMOUNT_ARG) ?: "250"

                TradeSuccessScreen(
                    symbol = symbol,
                    side = side,
                    amount = amount,
                    onGoToActivity = {
                        val entry = "${side.uppercase()} • £$amount • $symbol"
                        TradeLogStore.add(entry)

                        navController.navigate(Routes.ACTIVITY) {
                            popUpTo(Routes.PORTFOLIO) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onViewAsset = {
                        navController.navigate(Routes.asset(symbol)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Routes.GALLERY) {
                val links = listOf(
                    GalleryLink("Portfolio", "Hero portfolio overview", Routes.PORTFOLIO),
                    GalleryLink("Watchlist", "Saved assets list + search", Routes.WATCHLIST),
                    GalleryLink("Asset Detail", "Chart + stats + trade panel", Routes.asset("BTC")),
                    GalleryLink("Trade Ticket", "Buy/Sell ticket", Routes.trade("BTC")),
                    GalleryLink("Activity", "Recent activity feed", Routes.ACTIVITY)
                )

                UiKitGalleryScreen(
                    links = links,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        }
    }
}