package com.example.fintechui.ui.screens.portfolio

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.fintechui.ui.components.TimeChip
import com.example.fintechui.ui.components.demoSeries
import com.example.fintechui.ui.theme.FintechUITheme

@Composable
fun PortfolioHeroScreen(
    modifier: Modifier = Modifier,
    onAssetClick: (String) -> Unit = {},
    onOpenWatchlist: () -> Unit = {}
) {
    val topAssets = listOf(
        AssetRowData(symbol = "BTC", name = "Bitcoin", holdings = "1.42", value = "£54,200", change = "▲"),
        AssetRowData(symbol = "AAPL", name = "Apple Inc", holdings = "24", value = "£3,480", change = "▼"),
        AssetRowData(symbol = "TSLA", name = "Tesla", holdings = "12", value = "£2,910", change = "▲"),
        AssetRowData(symbol = "MSFT", name = "Microsoft", holdings = "8", value = "£2,140", change = "▲"),
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { PortfolioHeader(onOpenWatchlist) }
            item { PerformanceCard() }
            item { QuickActionsRow() }
            item { SectionHeader(title = "Top Assets", actionText = "View all") }
            items(topAssets) { asset ->
                AssetRow(asset, onClick = { onAssetClick(asset.symbol) })
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun PortfolioHeader(onOpenWatchlist: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Total Portfolio Value",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "£124,580.42",
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChangePill(text = "▲ £1,284.21  (+1.04%)", positive = true)

            Text(
                text = "Today",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Watchlist",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onOpenWatchlist() }
            )
        }
    }
}

@Composable
private fun PerformanceCard() {
    DeskCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
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
                Text(
                    "Performance",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Balance • Equity",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            LineChart(
                points = demoSeries(symbol = "PORTFOLIO", timeframeLabel = "1D"),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                showGrid = true
            )

            // Static chips for now (portfolio doesn't need live timeframe yet)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimeChip("1D", selected = true, onClick = { })
                TimeChip("1W", selected = false, onClick = { })
                TimeChip("1M", selected = false, onClick = { })
                TimeChip("1Y", selected = false, onClick = { })
                TimeChip("ALL", selected = false, onClick = { })
            }
        }
    }
}

@Composable
private fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickAction("Buy", "➕", Modifier.weight(1f))
        QuickAction("Sell", "➖", Modifier.weight(1f))
        QuickAction("Transfer", "↔", Modifier.weight(1f))
        QuickAction("History", "🕒", Modifier.weight(1f))
    }
}

@Composable
private fun QuickAction(label: String, icon: String, modifier: Modifier = Modifier) {
    DeskCard(
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(icon, fontSize = 18.sp)
            Text(
                label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        Text(actionText, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
    }
}

private data class AssetRowData(
    val symbol: String,
    val name: String,
    val holdings: String,
    val value: String,
    val change: String
)

@Composable
private fun AssetRow(
    asset: AssetRowData,
    onClick: () -> Unit
) {
    DeskCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(asset.symbol.take(1), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(asset.symbol, fontWeight = FontWeight.SemiBold)
                    Text(
                        asset.name,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "${asset.holdings} • ${asset.value}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                asset.change,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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