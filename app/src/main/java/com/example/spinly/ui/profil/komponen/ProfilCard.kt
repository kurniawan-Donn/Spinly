package com.example.spinly.ui.profil.komponen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val CardBg = Color(0xFF0F1B3D)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFB7C2E0)
private val AvatarBg = Color(0xFF25345A)
private val AvatarIconTint = Color(0xFFC7D2FE)
private val BadgeGreen = Color(0xFF22C55E)

@Composable
fun ProfilCard(
    nama: String,
    email: String,
    fotoProfil: String?,
    sudahLogin: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            // FIX: ganti != null dengan !isNullOrBlank()
            // null check saja tidak cukup — string kosong "" akan dikirim ke AsyncImage
            // dan gagal load tanpa menampilkan placeholder
            if (!fotoProfil.isNullOrBlank()) {
                AsyncImage(
                    model = fotoProfil,
                    contentDescription = "Foto profil",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(AvatarBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = AvatarIconTint,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            if (sudahLogin) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(BadgeGreen)
                        .border(2.dp, CardBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Akun terverifikasi",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = nama,
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (sudahLogin) email else "-",
            color = TextSecondary,
            fontSize = 14.sp
        )
    }
}