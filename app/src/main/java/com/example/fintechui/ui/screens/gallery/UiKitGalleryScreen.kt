package com.example.fintechui.ui.screens.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintechui.ui.components.ChangePill
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.components.LineChart
import com.example.fintechui.ui.components.MiniChangeChip
import com.example.fintechui.ui.components.PriceChip
import com.example.fintechui.ui.components.TimeChip
import com.example.fintechui.ui.components.demoSeries
import com.example.fintechui.ui.theme.FintechUITheme

data class GalleryLink(
    val title: String,
    val subtitle: String,
    val route: String
)

@Composable
fun UiKitGalleryScreen(
    modifier: Modifier = Modifier,
    links: List<GalleryLink>,
    onNavigate: (String) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Header() }

            item { SectionTitle("Components") }
            item { ComponentCards() }
            item { ComponentChips() }
            item { ComponentChart() }

            item { Spacer(Modifier.height(8.dp)) }
            item { SectionTitle("Screens") }
            items(links) { link ->
                ScreenLinkCard(
                    title = link.title,
                    subtitle = link.subtitle,
                    onClick = { onNavigate(link.route) }
                )
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun Header() {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "Fintech Desk UI Kit",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "A gallery of reusable components and demo screens.",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun ComponentCards() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        DeskCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(18.dp)) {
            Text("DeskCard", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(
                "Standard container card.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        DeskCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(22.dp)) {
            Text("Rounded", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(
                "22dp radius variant.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ComponentChips() {
    DeskCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
        Text("Chips & Pills", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TimeChip("1D", selected = true, onClick = { })
            TimeChip("1W", selected = false, onClick = { })
            TimeChip("1M", selected = false, onClick = { })
            TimeChip("ALL", selected = false, onClick = { })
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MiniChangeChip(text = "▲ 1.62%", positive = true)
            MiniChangeChip(text = "▼ 0.74%", positive = false)
            PriceChip(text = "£38,420")
        }

        Spacer(Modifier.height(12.dp))

        ChangePill(text = "▲ £612.44 (+1.62%)", positive = true)
        Spacer(Modifier.height(8.dp))
        ChangePill(text = "▼ £1.36 (-0.74%)", positive = false)
    }
}

@Composable
private fun ComponentChart() {
    DeskCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Text("LineChart (Canvas)", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))

        LineChart(
            points = demoSeries(symbol = "BTC", timeframeLabel = "1D"),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            showGrid = true
        )
    }
}

@Composable
private fun ScreenLinkCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    DeskCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        Text(
            subtitle,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))

        // simple “button-ish” row without needing Button imports
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Open →",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UiKitGalleryScreenPreview() {
    FintechUITheme {
        UiKitGalleryScreen(
            links = listOf(
                GalleryLink("Portfolio", "Hero portfolio overview", "portfolio"),
                GalleryLink("Watchlist", "Saved assets list + search", "watchlist"),
                GalleryLink("Asset Detail", "Chart + stats + trade panel", "asset/BTC"),
                GalleryLink("Trade Ticket", "Buy/Sell ticket", "trade/BTC"),
                GalleryLink("Activity", "Recent activity feed", "activity")
            ),
            onNavigate = {}
        )
    }
}