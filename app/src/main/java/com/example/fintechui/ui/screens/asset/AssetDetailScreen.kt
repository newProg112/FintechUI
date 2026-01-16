package com.example.fintechui.ui.screens.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fintechui.ui.components.ChangePill
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.components.LineChart
import com.example.fintechui.ui.components.MiniChangeChip
import com.example.fintechui.ui.components.PriceChip
import com.example.fintechui.ui.components.TimeChip
import com.example.fintechui.ui.components.demoSeries
import com.example.fintechui.ui.theme.FintechUITheme

enum class Timeframe(val label: String) {
    D1("1D"), W1("1W"), M1("1M"), Y1("1Y"), ALL("ALL")
}

data class AssetSnapshot(
    val price: String,
    val changePctText: String,
    val changePositive: Boolean,
    val changeDetailText: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailRoute(
    symbol: String,
    name: String,
    meta: String = "Spot",
    onBack: () -> Unit
) {
    var timeframe by remember { mutableStateOf(Timeframe.D1) }
    val snap = remember(symbol, timeframe) { fakeSnapshotFor(symbol, timeframe) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "$symbol • $meta",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(end = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MiniChangeChip(text = snap.changePctText, positive = snap.changePositive)
                        PriceChip(text = snap.price)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        AssetDetailScreen(
            modifier = Modifier.padding(innerPadding),
            symbol = symbol,
            name = name,
            price = snap.price,
            changeText = snap.changeDetailText,
            changePositive = snap.changePositive,
            selectedTimeframe = timeframe,
            onTimeframeSelected = { timeframe = it }
        )
    }
}

@Composable
fun AssetDetailScreen(
    modifier: Modifier = Modifier,
    symbol: String = "BTC",
    name: String = "Bitcoin",
    price: String = "£38,420.10",
    changeText: String = "▲ £612.44 (+1.62%)",
    changePositive: Boolean = true,
    selectedTimeframe: Timeframe = Timeframe.D1,
    onTimeframeSelected: (Timeframe) -> Unit = {}
) {
    val stats = listOf(
        StatPill("Market Cap", "£755B"),
        StatPill("24h Vol", "£18.2B"),
        StatPill("Day Range", "£37.9k–£38.7k"),
        StatPill("Holdings", "1.42 $symbol")
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { AssetHeader(symbol = symbol, name = name) }

            item {
                PriceBlock(
                    price = price,
                    changeText = changeText,
                    changePositive = changePositive
                )
            }

            item {
                AssetChartCard(
                    symbol = symbol,
                    selected = selectedTimeframe,
                    onSelected = onTimeframeSelected
                )
            }

            item { StatPillsGrid(stats = stats) }

            item { HoldingsCard(symbol = symbol) }

            item { OrderPanel() }

            item {
                SectionHeader(
                    title = "Recent Activity",
                    actionText = "View all"
                )
            }

            items(
                listOf(
                    "Bought 0.12 $symbol • £4,612.10",
                    "Price alert triggered • £38,000",
                    "Transferred in • 0.30 $symbol",
                    "Sold 0.05 $symbol • £1,921.00"
                )
            ) { row ->
                ActivityRow(text = row)
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun AssetHeader(symbol: String, name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(symbol.take(1), fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(symbol, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(name, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                    shape = CircleShape
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text("Watch", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun PriceBlock(price: String, changeText: String, changePositive: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Live Price",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = price,
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        ChangePill(text = changeText, positive = changePositive)
    }
}

@Composable
private fun AssetChartCard(
    symbol: String,
    selected: Timeframe,
    onSelected: (Timeframe) -> Unit
) {
    DeskCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Price Chart", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                Text(
                    "USD • Spot",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            LineChart(
                points = demoSeries(symbol = symbol, timeframeLabel = selected.label),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                showGrid = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimeChip("1D", selected = selected == Timeframe.D1, onClick = { onSelected(Timeframe.D1) })
                TimeChip("1W", selected = selected == Timeframe.W1, onClick = { onSelected(Timeframe.W1) })
                TimeChip("1M", selected = selected == Timeframe.M1, onClick = { onSelected(Timeframe.M1) })
                TimeChip("1Y", selected = selected == Timeframe.Y1, onClick = { onSelected(Timeframe.Y1) })
                TimeChip("ALL", selected = selected == Timeframe.ALL, onClick = { onSelected(Timeframe.ALL) })
            }
        }
    }
}

data class StatPill(val label: String, val value: String)

@Composable
private fun StatPillsGrid(stats: List<StatPill>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(stats[0], Modifier.weight(1f))
            StatCard(stats[1], Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(stats[2], Modifier.weight(1f))
            StatCard(stats[3], Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(stat: StatPill, modifier: Modifier = Modifier) {
    DeskCard(
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(stat.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(stat.value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun HoldingsCard(symbol: String) {
    DeskCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Your Position", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("1.42 $symbol", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text("Avg cost: £31,240", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Value", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("£54,200", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                ChangePill(text = "▲ +£3,110", positive = true)
            }
        }
    }
}

@Composable
private fun OrderPanel() {
    DeskCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Trade", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
                Text("Market", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Amount", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("£250.00", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                }
                Text("Edit", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TradeButton(text = "Buy", emphasis = true, modifier = Modifier.weight(1f))
                TradeButton(text = "Sell", emphasis = false, modifier = Modifier.weight(1f))
            }

            Text(
                "Fees est. £0.48 • Slippage low",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TradeButton(text: String, emphasis: Boolean, modifier: Modifier = Modifier) {
    val bg = if (emphasis) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (emphasis) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(bg)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                shape = RoundedCornerShape(18.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = fg)
    }
}

@Composable
private fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        Text(actionText, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ActivityRow(text: String) {
    DeskCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, style = MaterialTheme.typography.bodyMedium)
            Text("›", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun fakeSnapshotFor(symbol: String, tf: Timeframe): AssetSnapshot {
    return when (symbol) {
        "AAPL" -> when (tf) {
            Timeframe.D1 -> AssetSnapshot("£182.40", "▼ 0.74%", false, "▼ £1.36 (-0.74%)")
            Timeframe.W1 -> AssetSnapshot("£182.40", "▲ 1.10%", true, "▲ £1.98 (+1.10%)")
            Timeframe.M1 -> AssetSnapshot("£182.40", "▲ 4.60%", true, "▲ £8.02 (+4.60%)")
            Timeframe.Y1 -> AssetSnapshot("£182.40", "▲ 21.3%", true, "▲ £31.98 (+21.3%)")
            Timeframe.ALL -> AssetSnapshot("£182.40", "▲ 148%", true, "▲ +148% (All time)")
        }
        "TSLA" -> when (tf) {
            Timeframe.D1 -> AssetSnapshot("£211.15", "▲ 2.10%", true, "▲ £4.35 (+2.10%)")
            Timeframe.W1 -> AssetSnapshot("£211.15", "▼ 1.80%", false, "▼ £3.87 (-1.80%)")
            Timeframe.M1 -> AssetSnapshot("£211.15", "▲ 6.40%", true, "▲ £12.70 (+6.40%)")
            Timeframe.Y1 -> AssetSnapshot("£211.15", "▲ 12.8%", true, "▲ £24.00 (+12.8%)")
            Timeframe.ALL -> AssetSnapshot("£211.15", "▲ 64%", true, "▲ +64% (All time)")
        }
        "MSFT" -> when (tf) {
            Timeframe.D1 -> AssetSnapshot("£321.08", "▲ 0.38%", true, "▲ £1.22 (+0.38%)")
            Timeframe.W1 -> AssetSnapshot("£321.08", "▲ 2.40%", true, "▲ £7.50 (+2.40%)")
            Timeframe.M1 -> AssetSnapshot("£321.08", "▲ 3.90%", true, "▲ £12.05 (+3.90%)")
            Timeframe.Y1 -> AssetSnapshot("£321.08", "▲ 18.2%", true, "▲ £49.55 (+18.2%)")
            Timeframe.ALL -> AssetSnapshot("£321.08", "▲ 210%", true, "▲ +210% (All time)")
        }
        else -> when (tf) { // BTC default
            Timeframe.D1 -> AssetSnapshot("£38,420.10", "▲ 1.62%", true, "▲ £612.44 (+1.62%)")
            Timeframe.W1 -> AssetSnapshot("£38,420.10", "▲ 3.20%", true, "▲ £1,190 (+3.20%)")
            Timeframe.M1 -> AssetSnapshot("£38,420.10", "▼ 4.10%", false, "▼ £1,640 (-4.10%)")
            Timeframe.Y1 -> AssetSnapshot("£38,420.10", "▲ 58.0%", true, "▲ £14,100 (+58.0%)")
            Timeframe.ALL -> AssetSnapshot("£38,420.10", "▲ 900%", true, "▲ +900% (All time)")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetDetailScreenPreview() {
    FintechUITheme {
        AssetDetailRoute(
            symbol = "BTC",
            name = "Bitcoin",
            onBack = {}
        )
    }
}