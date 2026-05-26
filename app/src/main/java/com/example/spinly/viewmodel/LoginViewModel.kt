package com.example.spinly.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _loginError =
        MutableStateFlow<String?>(null)

    val loginError =
        _loginError.asStateFlow()

    fun loginGuest(
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            auth.signInAnonymously()
                .addOnSuccessListener {

                    onSuccess()
                }
                .addOnFailureListener {

                    _loginError.value =
                        "Login tamu gagal"
                }
        }
    }

    fun clearError() {
        _loginError.value = null
    }
}