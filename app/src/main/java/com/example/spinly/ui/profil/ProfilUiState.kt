package com.example.spinly.ui.profil

data class ProfileUiState(
    val nama: String = "Tamu",
    val email: String = "",
    val fotoProfil: String? = null,
    val sudahLogin: Boolean = false,
    val isAnonymous: Boolean = true,
    val riwayatRoom: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

