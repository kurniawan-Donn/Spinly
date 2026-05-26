package com.example.spinly.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spinly.ui.theme.SpinColors

@Composable
fun JoinRoomScreen(
    onJoinSuccess: (String) -> Unit,
) {
    var digits by remember { mutableStateOf(List(6) { "" }) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpinColors.BgDark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text = "Join Room",
            color = SpinColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Masuk ke Room",
            color = SpinColors.TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Masukkan kode 6 digit dari host untuk bergabung ke room.",
            color = SpinColors.TextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Kode Room",
            color = SpinColors.TextSecondary,
            fontSize = 13.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            digits.forEachIndexed { index, digit ->
                OutlinedTextField(
                    value = digit,
                    onValueChange = { new ->
                        val filtered = new.filter { it.isDigit() }.take(1)
                        val updated = digits.toMutableList()
                        updated[index] = filtered
                        digits = updated
                        errorMsg = null
                        if (filtered.isNotEmpty() && index < 5) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .focusRequester(focusRequesters[index]),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = SpinColors.TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpinColors.AccentBlue,
                        unfocusedBorderColor = SpinColors.Divider,
                        focusedContainerColor = SpinColors.BgCard,
                        unfocusedContainerColor = SpinColors.BgCard,
                        cursorColor = SpinColors.AccentBlue,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = if (index == 5) ImeAction.Done else ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                )
            }
        }

        if (errorMsg != null) {
            Spacer(Modifier.height(8.dp))
            Text(errorMsg!!, color = SpinColors.AccentRed, fontSize = 12.sp)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val code = digits.joinToString("")
                if (code.length < 6) {
                    errorMsg = "Masukkan kode 6 digit terlebih dahulu"
                } else {
                    isLoading = true
                    onJoinSuccess(code)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !isLoading,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SpinColors.AccentBlue,
            ),
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = "Join Room",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = SpinColors.Divider,
                thickness = 1.dp,
            )
            Text(
                text = "atau",
                color = SpinColors.TextSecondary,
                fontSize = 13.sp,
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = SpinColors.Divider,
                thickness = 1.dp,
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = { onJoinSuccess("NEW") },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, SpinColors.AccentBlue),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = SpinColors.TextPrimary,
            ),
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = SpinColors.TextPrimary,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Buat Room Baru",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = SpinColors.BgCard,
            ),
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = SpinColors.AccentBlue,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Tips",
                        color = SpinColors.TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Tanyakan kode room ke host atau teman kamu.",
                        color = SpinColors.TextSecondary,
                        fontSize = 12.sp,
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JoinRoomScreenPreview() {
    MaterialTheme {
        JoinRoomScreen(
            onJoinSuccess = {}
        )
    }
}