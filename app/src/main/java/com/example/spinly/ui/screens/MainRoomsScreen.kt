package com.example.spinly.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spinly.ui.components.SpinWheelCanvas
import com.example.spinly.ui.components.rememberSpinWheelState
import com.example.spinly.ui.theme.SpinColors
import kotlinx.coroutines.launch

@Composable
fun MainRoomScreen(
    roomCode: String = "",
    onProfile: () -> Unit = {},
    onLeave: () -> Unit = {},
) {
    var itemList by remember { mutableStateOf(mutableListOf<String>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var spinResult by remember { mutableStateOf("") }
    val spinWheelState = rememberSpinWheelState()
    val scope = rememberCoroutineScope()  // diperlukan untuk memanggil suspend function

    val colors = listOf(
        Color(0xFFE63946), Color(0xFF457B9D), Color(0xFF2A9D8F),
        Color(0xFFE9C46A), Color(0xFFF4A261), Color(0xFF6A4C93),
        Color(0xFF9C89B8), Color(0xFFF08080), Color(0xFF81B29A),
        Color(0xFFF2CC8F), Color(0xFFE07A5F), Color(0xFF3D405B)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpinColors.BgDark)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "SPINLY",
            color = SpinColors.TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Gunakan SpinWheelCanvas dengan state (tanpa rotationAngle)
        SpinWheelCanvas(
            items = itemList,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp),
            state = spinWheelState,
            onSpinEnd = { result -> spinResult = result }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch { spinWheelState.spin() }  // panggil suspend function di dalam coroutine
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SpinColors.AccentBlue),
            enabled = itemList.isNotEmpty()
        ) {
            Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("PUTAR", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("DAFTAR ITEM", color = SpinColors.TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, null, tint = SpinColors.AccentBlue, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("TAMBAH DAFTAR", color = SpinColors.AccentBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (itemList.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada item. Tambahkan dengan tombol di atas.",
                        color = SpinColors.TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else {
                items(itemList) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(colors[itemList.indexOf(item) % colors.size])
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(item, color = SpinColors.TextPrimary, fontSize = 16.sp, modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = SpinColors.TextSecondary,
                            modifier = Modifier.size(20.dp).clickable {
                                itemList = itemList.toMutableList().apply { remove(item) }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Hasil Spin", color = SpinColors.TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SpinColors.BgCard)
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (spinResult.isEmpty()) {
                Text("Belum ada spin", color = SpinColors.TextSecondary, fontSize = 14.sp)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SpinColors.AccentGreen)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(spinResult, color = SpinColors.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showAddDialog) {
        var newItemText by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Tambah Item") },
            text = {
                OutlinedTextField(
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    label = { Text("Nama item") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newItemText.isNotBlank()) {
                            itemList = itemList.toMutableList().apply { add(newItemText) }
                        }
                        showAddDialog = false
                    }
                ) { Text("Tambah") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Batal") }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainRoomScreenPreview() {
    MaterialTheme {
        MainRoomScreen()
    }
}