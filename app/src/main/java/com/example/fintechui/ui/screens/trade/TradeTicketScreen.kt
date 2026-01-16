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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.screens.asset.AssetDetailScreen
import com.example.fintechui.ui.theme.FintechUITheme

private enum class Side { BUY, SELL }
private enum class OrderType(val label: String) { MARKET("Market"), LIMIT("Limit") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeTicketRoute(
    symbol: String,
    onBack: () -> Unit,
    onReview: (side: String, amount: String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Trade $symbol") },
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
        TradeTicketScreen(
            modifier = Modifier.padding(innerPadding),
            symbol = symbol,
            onReview = onReview
        )
    }
}

@Composable
private fun TradeTicketScreen(
    modifier: Modifier = Modifier,
    symbol: String,
    onReview: (side: String, amount: String) -> Unit
) {
    var side by remember { mutableStateOf(Side.BUY) }
    var orderType by remember { mutableStateOf(OrderType.MARKET) }
    var amount by remember { mutableStateOf("250") } // fake £ amount input

    var limitPrice by remember { mutableStateOf("38420") } // demo limit price in GBP

    val amountDouble = amount.toDoubleOrNull() ?: 0.0
    val px = if (orderType == OrderType.LIMIT) (limitPrice.toDoubleOrNull() ?: fakePriceGbp(symbol)) else fakePriceGbp(symbol)
    val estUnits = if (px > 0) amountDouble / px else 0.0

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Side toggle
            DeskCard(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SegmentedButton(
                        text = "Buy",
                        selected = side == Side.BUY,
                        onClick = { side = Side.BUY },
                        modifier = Modifier.weight(1f)
                    )
                    SegmentedButton(
                        text = "Sell",
                        selected = side == Side.SELL,
                        onClick = { side = Side.SELL },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Symbol",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(text = symbol, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            // Order type
            DeskCard(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Order Type", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SegmentedButton(
                        text = OrderType.MARKET.label,
                        selected = orderType == OrderType.MARKET,
                        onClick = { orderType = OrderType.MARKET },
                        modifier = Modifier.weight(1f)
                    )
                    SegmentedButton(
                        text = OrderType.LIMIT.label,
                        selected = orderType == OrderType.LIMIT,
                        onClick = { orderType = OrderType.LIMIT },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(10.dp))
                Text(
                    text = if (orderType == OrderType.MARKET) "Executes at best available price."
                    else "Executes only at your set price (demo).",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (orderType == OrderType.LIMIT) {
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = limitPrice,
                        onValueChange = { limitPrice = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Limit price (GBP)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            // Amount (fake input style)
            DeskCard(shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Text("Amount", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(18.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("You pay", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("£$amount.00", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    }
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            // cycle some preset amounts so it “feels interactive”
                            amount = when (amount) {
                                "100" -> "250"
                                "250" -> "500"
                                "500" -> "1000"
                                else -> "100"
                            }
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Fees est. £0.48 • Slippage low",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Est. ${formatUnits(estUnits)} $symbol at £${String.format("%.2f", px)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.weight(1f))

            // Review button
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
                    .clickable {
                        onReview(
                            if (side == Side.BUY) "BUY" else "SELL",
                            amount
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Review Order",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun SegmentedButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .height(46.dp)
            .background(bg, RoundedCornerShape(14.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, color = fg)
    }
}

private fun fakePriceGbp(symbol: String): Double = when (symbol) {
    "BTC" -> 38420.0
    "ETH" -> 2140.0
    "AAPL" -> 182.40
    "TSLA" -> 211.15
    "MSFT" -> 321.08
    "NVDA" -> 490.22
    else -> 100.0
}

private fun formatUnits(units: Double): String {
    return if (units >= 1.0) String.format("%.3f", units) else String.format("%.5f", units)
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