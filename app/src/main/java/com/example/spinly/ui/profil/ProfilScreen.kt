package com.example.spinly.ui.profil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spinly.ui.profil.komponen.*

@Composable
fun ProfilRoute(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    vm: ProfileViewModel = viewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    ProfilScreen(
        state = state,
        onBackClick = onBackClick,
        onLogout = { vm.logout() },
        onLoginClick = onLoginClick
    )
}

@Composable
fun ProfilScreen(
    state: ProfileUiState,
    onBackClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050B1B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            ProfilTopBar(onBackClick = onBackClick)

            Spacer(modifier = Modifier.height(18.dp))

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
                return@Column
            }

            state.errorMessage?.let { err ->
                Text(
                    text = "Gagal memuat profil: $err",
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            ProfilCard(
                nama = state.nama,
                email = state.email,
                fotoProfil = state.fotoProfil,
                sudahLogin = state.sudahLogin
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatusCard(sudahLogin = state.sudahLogin)

            Spacer(modifier = Modifier.height(16.dp))

            RiwayatRoomCard(
                riwayatRoom = state.riwayatRoom,
                onRoomClick = { roomName ->
                    // TODO: navigasi ke detail room
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            if (state.sudahLogin) {
                ButtonLogout(onClick = onLogout)
            } else {
                ButtonLogin(onClick = onLoginClick)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfilLogin() {
    ProfilScreen(
        state = ProfileUiState(
            nama = "Hotaro Yuki",
            email = "hotaro@gmail.com",
            fotoProfil = null,
            sudahLogin = true,
            riwayatRoom = listOf("Anime Room", "Gaming Room", "Nongkrong Room")
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfilTamu() {
    ProfilScreen(
        state = ProfileUiState(
            nama = "Tamu",
            email = "",
            fotoProfil = null,
            sudahLogin = false,
            riwayatRoom = emptyList()
        )
    )
}