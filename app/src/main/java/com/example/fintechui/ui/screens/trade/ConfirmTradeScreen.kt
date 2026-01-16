package com.example.fintechui.ui.screens.trade

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintechui.ui.components.ChangePill
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.theme.FintechUITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmTradeRoute(
    symbol: String,
    side: String,
    amount: String,
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Confirm") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        ConfirmTradeScreen(
            modifier = Modifier.padding(innerPadding),
            symbol = symbol,
            side = side,
            amount = amount,
            onDone = onDone
        )
    }
}

@Composable
private fun ConfirmTradeScreen(
    modifier: Modifier = Modifier,
    symbol: String,
    side: String,
    amount: String,
    onDone: () -> Unit
) {
    val positive = side.uppercase() == "BUY"

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            DeskCard(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Order Summary", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))

                SummaryRow("Side", side.uppercase())
                SummaryRow("Symbol", symbol)
                SummaryRow("Order Type", "Market")
                SummaryRow("Amount", "£$amount.00")
                SummaryRow("Fees (est.)", "£0.48")

                Spacer(Modifier.height(12.dp))
                ChangePill(
                    text = if (positive) "Ready to Buy" else "Ready to Sell",
                    positive = positive
                )
            }

            DeskCard(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Risk note", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Text(
                    "This is a demo flow. No real orders are placed.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clickable(onClick = onDone),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Place Order",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
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