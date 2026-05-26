package com.example.spinly.ui.profil.komponen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusCard(
    sudahLogin: Boolean
) {

    val backgroundBrush =
        if (sudahLogin) {

            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF174D2E),
                    Color(0xFF0F7B3A)
                )
            )

        } else {

            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFF0B90B),
                    Color(0xFFE2A500)
                )
            )
        }

    val icon =
        if (sudahLogin) {
            Icons.Default.Security
        } else {
            Icons.Default.Warning
        }

    val title =
        if (sudahLogin) {
            "Data aman & tersinkronisasi"
        } else {
            "Hubungkan ke Google"
        }

    val subtitle =
        if (sudahLogin) {
            "Semua data kamu terlindungi."
        } else {
            "Agar data tidak hilang dan tersimpan aman."
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = backgroundBrush,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 12.sp
            )
        }
    }
}