package com.example.spinly.ui.profil.komponen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBg = Color(0xFF0F1B3D)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFB7C2E0)
private val DividerColor = Color(0xFF22315C)
private val AccentBlue = Color(0xFF2962FF)

@Composable
fun RiwayatRoomCard(
    riwayatRoom: List<String>,
    onRoomClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Riwayat Room",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            if (riwayatRoom.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(52.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Belum ada riwayat room.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Mulai buat atau join room sekarang!",
                        color = TextSecondary.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            } else {
                riwayatRoom.forEachIndexed { index, room ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRoomClick(room) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                        ) {
                            androidx.compose.foundation.Canvas(modifier = Modifier.size(10.dp)) {
                                drawCircle(color = AccentBlue)
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = room,
                            color = TextPrimary,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Buka room $room",
                            tint = TextSecondary
                        )
                    }

                    if (index < riwayatRoom.lastIndex) {
                        HorizontalDivider(
                            color = DividerColor,
                            thickness = .5.dp
                        )
                    }
                }
            }
        }
    }
}