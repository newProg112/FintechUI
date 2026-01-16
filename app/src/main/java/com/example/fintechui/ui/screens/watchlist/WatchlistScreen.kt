package com.example.fintechui.ui.screens.watchlist

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.components.MiniChangeChip
import com.example.fintechui.ui.components.TimeChip
import com.example.fintechui.ui.theme.FintechUITheme

data class WatchItem(
    val symbol: String,
    val name: String,
    val price: String,
    val changePct: String,
    val changePositive: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistRoute(
    onAssetClick: (String) -> Unit
) {
    WatchlistScreen(onAssetClick = onAssetClick)
}

@Composable
fun WatchlistScreen(
    modifier: Modifier = Modifier,
    onAssetClick: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val items = remember {
        listOf(
            WatchItem("BTC", "Bitcoin", "£38,420", "▲ 1.62%", true),
            WatchItem("ETH", "Ethereum", "£2,140", "▼ 0.80%", false),
            WatchItem("AAPL", "Apple Inc", "£182.40", "▼ 0.74%", false),
            WatchItem("TSLA", "Tesla", "£211.15", "▲ 2.10%", true),
            WatchItem("MSFT", "Microsoft", "£321.08", "▲ 0.38%", true),
            WatchItem("NVDA", "NVIDIA", "£490.22", "▲ 1.05%", true),
        )
    }

    val filtered = remember(query, items) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) items
        else items.filter {
            it.symbol.lowercase().contains(q) || it.name.lowercase().contains(q)
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Watchlist",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Saved assets • Tap to open",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Search") },
                    placeholder = { Text("BTC, Apple, Tesla…") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimeChip("All", selected = true, onClick = { })
                    TimeChip("Crypto", selected = false, onClick = { })
                    TimeChip("Stocks", selected = false, onClick = { })
                }
            }

            item {
                Text(
                    text = "Saved",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(filtered) { item ->
                WatchlistRow(
                    item = item,
                    onClick = { onAssetClick(item.symbol) }
                )
            }

            if (filtered.isEmpty()) {
                item {
                    DeskCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                "No matches",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Try searching by symbol (e.g. TSLA) or name.",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun WatchlistRow(
    item: WatchItem,
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
                Text(item.symbol.take(1), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.symbol, fontWeight = FontWeight.SemiBold)
                    Text(item.name, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(item.price, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            }

            MiniChangeChip(
                text = item.changePct,
                positive = item.changePositive
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistScreenPreview() {
    FintechUITheme {
        WatchlistScreen(onAssetClick = {})
    }
}