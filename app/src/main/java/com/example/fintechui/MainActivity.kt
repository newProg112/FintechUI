package com.example.fintechui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.fintechui.ui.navigation.AppNavHost
import com.example.fintechui.ui.screens.activity.ActivityScreen
import com.example.fintechui.ui.screens.asset.AssetDetailScreen
import com.example.fintechui.ui.screens.portfolio.PortfolioHeroScreen
import com.example.fintechui.ui.screens.trade.ConfirmTradeRoute
import com.example.fintechui.ui.screens.trade.TradeSuccessScreen
import com.example.fintechui.ui.screens.trade.TradeTicketRoute
import com.example.fintechui.ui.screens.watchlist.WatchlistScreen
import com.example.fintechui.ui.theme.FintechUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FintechUITheme {
                val view = LocalView.current
                val color = MaterialTheme.colorScheme.background.toArgb()

                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = color
                    window.navigationBarColor = color

                    val insetsController = WindowCompat.getInsetsController(window, view)
                    // Dark background -> light icons should be FALSE (i.e. don't use dark icons)
                    insetsController.isAppearanceLightStatusBars = false
                    insetsController.isAppearanceLightNavigationBars = false
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PortfolioHeroScreenPreview() {
    FintechUITheme {
        PortfolioHeroScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetDetailScreenPreview() {
    FintechUITheme {
        AssetDetailScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistScreenPreview() {
    FintechUITheme {
        WatchlistScreen(
            onAssetClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityScreenPreview() {
    FintechUITheme {
        ActivityScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun TradeTicketScreenPreview() {
    FintechUITheme {
        // Preview the *screen* by calling the route and passing no-op lambdas
        TradeTicketRoute(
            symbol = "BTC",
            onBack = {},
            onReview = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmTradeScreenPreview() {
    FintechUITheme {
        ConfirmTradeRoute(
            symbol = "BTC",
            side = "BUY",
            amount = "250",
            onBack = {},
            onDone = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmTradeScreenSellPreview() {
    FintechUITheme {
        ConfirmTradeRoute(
            symbol = "TSLA",
            side = "SELL",
            amount = "500",
            onBack = {},
            onDone = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TradeSuccessScreenPreview() {
    FintechUITheme {
        TradeSuccessScreen(
            symbol = "BTC",
            side = "BUY",
            amount = "250",
            onGoToActivity = {},
            onViewAsset = {}
        )
    }
}