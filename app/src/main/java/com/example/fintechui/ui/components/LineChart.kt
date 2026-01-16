package com.example.fintechui.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintechui.ui.screens.activity.ActivityScreen
import com.example.fintechui.ui.theme.FintechUITheme
import kotlin.math.max
import kotlin.math.min

@Composable
fun LineChart(
    points: List<Float>,
    modifier: Modifier = Modifier,
    rounded: RoundedCornerShape = RoundedCornerShape(18.dp),
    showGrid: Boolean = true
) {
    val bg = MaterialTheme.colorScheme.surfaceVariant
    val outline = MaterialTheme.colorScheme.outline.copy(alpha = 0.30f)
    val line = MaterialTheme.colorScheme.primary
    val grid = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)

    Canvas(
        modifier = modifier
            .clip(rounded)
            .background(bg)
            .border(1.dp, outline, rounded)
            .fillMaxSize()
    ) {
        if (points.size < 2) return@Canvas

        val w = size.width
        val h = size.height

        val minY = points.minOrNull() ?: 0f
        val maxY = points.maxOrNull() ?: 1f
        val range = max(0.0001f, maxY - minY)

        fun yFor(v: Float): Float {
            // 10% top/bottom padding
            val t = (v - minY) / range
            val padded = 0.10f + (1f - 0.20f) * (1f - t)
            return padded * h
        }

        val stepX = w / (points.size - 1)

        // optional grid
        if (showGrid) {
            val rows = 3
            val cols = 4
            for (r in 1..rows) {
                val y = (r.toFloat() / (rows + 1)) * h
                drawLine(grid, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
            }
            for (c in 1..cols) {
                val x = (c.toFloat() / (cols + 1)) * w
                drawLine(grid, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
            }
        }

        // build path
        val path = Path()
        path.moveTo(0f, yFor(points[0]))

        for (i in 1 until points.size) {
            val x = stepX * i
            val y = yFor(points[i])
            path.lineTo(x, y)
        }

        // draw line
        drawPath(
            path = path,
            color = line,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
        )

        // draw last dot
        val lastX = w
        val lastY = yFor(points.last())
        drawCircle(color = line, radius = 7f, center = Offset(lastX, lastY))
        drawCircle(color = bg, radius = 3.5f, center = Offset(lastX, lastY))
    }
}

/**
 * Fake-but-consistent data generator per symbol/timeframe.
 * Returns normalized “price-like” values (no need to be real).
 */
fun demoSeries(symbol: String, timeframeLabel: String): List<Float> {
    val base = when (symbol) {
        "BTC" -> 40f
        "ETH" -> 24f
        "AAPL" -> 18f
        "TSLA" -> 22f
        "MSFT" -> 20f
        "NVDA" -> 26f
        else -> 16f
    }

    val volatility = when (timeframeLabel) {
        "1D" -> 1.6f
        "1W" -> 2.6f
        "1M" -> 3.6f
        "1Y" -> 5.0f
        "ALL" -> 7.0f
        else -> 2.0f
    }

    // deterministic wiggle
    val n = 24
    val out = ArrayList<Float>(n)
    var v = base

    for (i in 0 until n) {
        val wave = kotlin.math.sin(i * 0.55f) * (volatility * 0.35f)
        val drift = (i / (n - 1f)) * (volatility * 0.25f)
        val noise = kotlin.math.sin(i * 1.7f + base) * (volatility * 0.12f)

        v = base + wave + drift + noise
        out.add(v)
    }

    // ensure the series isn’t perfectly flat
    val minV = out.minOrNull() ?: base
    val maxV = out.maxOrNull() ?: base + 1f
    if (minV == maxV) {
        out[0] = out[0] - 1f
        out[out.lastIndex] = out[out.lastIndex] + 1f
    }

    return out
}

@Preview(showBackground = true)
@Composable
private fun LineChartPreview() {
    FintechUITheme {
        Box(Modifier.padding(16.dp).size(320.dp, 180.dp)) {
            LineChart(
                points = demoSeries(symbol = "BTC", timeframeLabel = "1D"),
                modifier = Modifier.fillMaxSize(),
                showGrid = true
            )
        }
    }
}