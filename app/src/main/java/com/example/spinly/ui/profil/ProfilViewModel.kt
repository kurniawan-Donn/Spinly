package com.example.spinly.ui.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfilViewModel(
    private val repository: ProfilRepository = ProfilRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ProfileUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        loadUser()
    }

    fun refresh() {
        loadUser()
    }

    private fun loadUser() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {

                val profile =
                    repository.getUserProfile()

                _uiState.value =
                    profile.copy(
                        isLoading = false,
                        errorMessage = null
                    )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun logout() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {

                repository.logout()

                _uiState.value =
                    ProfileUiState()

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}