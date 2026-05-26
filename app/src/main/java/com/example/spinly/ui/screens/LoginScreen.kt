package com.example.spinly.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spinly.R
import com.example.spinly.ui.theme.SpinColors
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(

    onLoginSuccess: () -> Unit,

    onGuestLogin: () -> Unit = {}
) {

    var isLoadingGoogle by remember {
        mutableStateOf(false)
    }

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    val scope = rememberCoroutineScope()

    Scaffold(

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )
        }

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(SpinColors.BgDark)
                .padding(paddingValues),

            contentAlignment = Alignment.Center
        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(48.dp))

                Box(

                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(

                        painter = painterResource(
                            id = R.drawable.icon
                        ),

                        contentDescription = "Logo Spinly",

                        tint = Color.Unspecified,

                        modifier = Modifier.size(90.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(

                    text = "Selamat Datang di Spinly",

                    color = SpinColors.TextPrimary,

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold,

                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(

                    text = "Silahkan Login terlebih dahulu untuk melanjutkan ke aplikasi",

                    color = SpinColors.TextSecondary,

                    fontSize = 14.sp,

                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(48.dp))

                Button(

                    onClick = {

                        isLoadingGoogle = true

                        try {

                            /*
                             TODO:
                             Integrasi Firebase Google Login
                            */

                            onLoginSuccess()

                        } catch (_: Exception) {

                            scope.launch {

                                snackbarHostState.showSnackbar(
                                    message = "Login Google gagal"
                                )
                            }

                        } finally {

                            isLoadingGoogle = false
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    enabled = !isLoadingGoogle,

                    shape = RoundedCornerShape(16.dp),

                    colors = ButtonDefaults.buttonColors(

                        containerColor = SpinColors.AccentBlue,

                        disabledContainerColor =
                            SpinColors.AccentBlue.copy(alpha = 0.5f)
                    )
                ) {

                    if (isLoadingGoogle) {

                        CircularProgressIndicator(

                            color = Color.White,

                            modifier = Modifier.size(20.dp),

                            strokeWidth = 2.dp
                        )

                    } else {

                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.Center
                        ) {

                            Icon(

                                painter = painterResource(
                                    id = R.drawable.ic_google
                                ),

                                contentDescription = "Google",

                                modifier = Modifier.size(20.dp),

                                tint = Color.Unspecified
                            )

                            Spacer(Modifier.width(12.dp))

                            Text(

                                text = "Masuk dengan Google",

                                fontWeight = FontWeight.Bold,

                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(

                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier.fillMaxWidth()
                ) {

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = SpinColors.Divider
                    )

                    Text(

                        text = " Atau ",

                        color = SpinColors.TextSecondary,

                        fontSize = 12.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = SpinColors.Divider
                    )
                }

                Spacer(Modifier.height(16.dp))

                OutlinedButton(

                    onClick = {

                        onGuestLogin()
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(16.dp),

                    border = BorderStroke(
                        1.dp,
                        SpinColors.AccentBlue
                    ),

                    colors = ButtonDefaults.outlinedButtonColors(

                        contentColor =
                            SpinColors.AccentBlue
                    )
                ) {

                    Text(

                        text = "Lanjut sebagai Tamu",

                        fontWeight = FontWeight.Bold,

                        fontSize = 15.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(

                    text = "Tamu bisa masuk ke akun Google tanpa kehilangan data",

                    color = SpinColors.TextSecondary,

                    fontSize = 11.sp,

                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {

    MaterialTheme {

        LoginScreen(

            onLoginSuccess = {},

            onGuestLogin = {}
        )
    }
}