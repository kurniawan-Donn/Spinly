package com.example.spinly.ui.profil

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfilRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun getUserProfile(): ProfileUiState = withContext(Dispatchers.IO) {
        val user = auth.currentUser

        if (user == null || user.isAnonymous) {
            return@withContext ProfileUiState(
                nama = "Tamu",
                email = "",
                fotoProfil = null,
                sudahLogin = false,
                isAnonymous = true,
                riwayatRoom = emptyList()
            )
        }

        // TODO: ganti listOf hardcoded ini dengan fetch dari Firestore/Room
        val riwayatRoom = listOf(
            "Main Bareng",
            "Nongkrong Santai",
            "Spin Seru"
        )

        ProfileUiState(
            nama = user.displayName ?: "Pengguna",
            email = user.email ?: "",
            fotoProfil = user.photoUrl?.toString(),
            sudahLogin = true,
            isAnonymous = false,
            riwayatRoom = riwayatRoom
        )
    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        auth.signOut()
    }
}
