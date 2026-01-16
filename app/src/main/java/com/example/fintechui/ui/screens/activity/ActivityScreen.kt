package com.example.fintechui.ui.screens.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.fintechui.data.TradeLogStore
import com.example.fintechui.ui.components.DeskCard
import com.example.fintechui.ui.theme.FintechUITheme

@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier
) {
    val baseRows = listOf(
        "Price alert triggered • £38,000",
        "Transferred in • 0.30 BTC",
        "Deposit • £1,000",
        "Dividend • £14.22"
    )

    // prepend logged trades (newest first)
    val rows = TradeLogStore.items + baseRows


    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            items(rows) { text ->
                DeskCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(text)
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityScreenPreview() {
    FintechUITheme {
        ActivityScreen()
    }
}