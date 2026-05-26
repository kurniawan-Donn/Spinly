package com.example.spinly.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpinWheelCanvas(
    items: List<String>,
    modifier: Modifier = Modifier,
    state: SpinWheelState = rememberSpinWheelState(),
    onSpinEnd: (String) -> Unit = {},
) {
    val colors = remember {
        listOf(
            Color(0xFFE63946), Color(0xFF457B9D), Color(0xFF2A9D8F),
            Color(0xFFE9C46A), Color(0xFFF4A261), Color(0xFF6A4C93),
            Color(0xFF9C89B8), Color(0xFFF08080), Color(0xFF81B29A),
            Color(0xFFF2CC8F), Color(0xFFE07A5F), Color(0xFF3D405B)
        )
    }

    val sectorCount = items.size
    val anglePerSector = if (sectorCount > 0) 360f / sectorCount else 0f

    var rotationAngle by remember { mutableStateOf(0f) }
    val animatedAngle = remember { Animatable(rotationAngle) }
    var isSpinning by remember { mutableStateOf(false) }

    suspend fun spin() {
        if (isSpinning || sectorCount == 0) return
        isSpinning = true
        val randomDegrees = (360 * 3 + (0..360).random()).toFloat()
        val targetRotation = rotationAngle + randomDegrees
        animatedAngle.animateTo(
            targetRotation,
            animationSpec = tween(2000, easing = FastOutSlowInEasing)
        )
        rotationAngle = targetRotation % 360
        val finalAngle = rotationAngle % 360
        val hitIndex = ((360 - finalAngle) % 360) / anglePerSector
        val selectedItem = items.getOrNull(hitIndex.toInt()) ?: ""
        onSpinEnd(selectedItem)
        isSpinning = false
    }

    LaunchedEffect(Unit) {
        state.spinAction = { spin() }
    }

    Canvas(
        modifier = modifier
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = minOf(size.width, size.height) / 2

        if (sectorCount == 0) {
            drawCircle(
                color = Color.Gray,
                radius = radius,
                style = Stroke(width = 4.dp.toPx())
            )
            drawCircle(
                color = Color.LightGray,
                radius = radius - 4.dp.toPx(),
                style = Stroke(width = 2.dp.toPx())
            )
            return@Canvas
        }

        val startAngle = -animatedAngle.value

        // Gambar sektor
        for (i in 0 until sectorCount) {
            val sweep = anglePerSector
            val start = startAngle + i * sweep
            val color = colors[i % colors.size]
            drawArc(
                color = color,
                startAngle = start,
                sweepAngle = sweep,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
        }

        // Garis pemisah
        for (i in 0 until sectorCount) {
            val angle = startAngle + i * anglePerSector
            val radians = Math.toRadians(angle.toDouble())
            val startX = center.x + (radius - 10) * Math.cos(radians).toFloat()
            val startY = center.y + (radius - 10) * Math.sin(radians).toFloat()
            val endX = center.x + radius * Math.cos(radians).toFloat()
            val endY = center.y + radius * Math.sin(radians).toFloat()
            drawLine(
                color = Color.White,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2.dp.toPx()
            )
        }

        // Lingkaran tepi
        drawCircle(
            color = Color.White,
            radius = radius,
            style = Stroke(width = 4.dp.toPx())
        )

        // Lingkaran tengah
        drawCircle(
            color = Color.White,
            radius = radius * 0.12f,
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.White,
            radius = radius * 0.08f,
            style = Stroke(width = 1.dp.toPx())
        )

        // Panah penunjuk (di atas, tepat di tengah)
        val arrowSize = 24.dp.toPx()
        val arrowTipY = center.y - radius - 8.dp.toPx()
        val arrowBaseY = center.y - radius + 12.dp.toPx()
        drawPath(
            path = Path().apply {
                moveTo(center.x - arrowSize / 2, arrowBaseY)
                lineTo(center.x, arrowTipY)
                lineTo(center.x + arrowSize / 2, arrowBaseY)
                close()
            },
            color = Color.White
        )
        // Lingkaran kecil di ujung panah
        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = Offset(center.x, arrowTipY - 2.dp.toPx())
        )
    }
}

@Composable
fun rememberSpinWheelState(): SpinWheelState {
    return remember { SpinWheelState() }
}

class SpinWheelState {
    var spinAction: (suspend () -> Unit)? = null
    suspend fun spin() {
        spinAction?.invoke()
    }
}