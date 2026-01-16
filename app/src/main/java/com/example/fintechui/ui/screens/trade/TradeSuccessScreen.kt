package com.example.fintechui.ui.screens.trade

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.fintechui.ui.theme.FintechUITheme

@Composable
fun TradeSuccessScreen(
    modifier: Modifier = Modifier,
    symbol: String,
    side: String,
    amount: String,
    onGoToActivity: () -> Unit,
    onViewAsset: () -> Unit
) {
    val positive = side.uppercase() == "BUY"

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", fontSize = 34.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onTertiaryContainer)
            }

            Text(
                text = "Order placed",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            ChangePill(
                text = "${side.uppercase()} • £$amount • $symbol",
                positive = positive
            )

            DeskCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Text(
                    text = "This is a demo confirmation. No real trades were executed.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "You can now view the asset or check Activity.",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.weight(1f))

            PrimaryActionButton(
                text = "Go to Activity",
                onClick = onGoToActivity
            )

            SecondaryActionButton(
                text = "View $symbol",
                onClick = onViewAsset
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PrimaryActionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun SecondaryActionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Keeps button tap feeling clean for a demo (no ripple).
 * If you prefer ripple, swap to Modifier.clickable(onClick = onClick)
 */
@Composable
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier {
    return this.then(
        androidx.compose.ui.Modifier
            .clickable(onClick = onClick)
    )
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