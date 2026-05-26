package com.example.spinly.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AutentikasiKlient(
    private val context: Context
) {

    private val auth = FirebaseAuth.getInstance()

    suspend fun signIn(): Result<Unit> {

        return try {

            val googleIdOption =
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        "ISI_WEB_CLIENT_ID_FIREBASE"
                    )
                    .build()

            val request =
                GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

            val credentialManager =
                CredentialManager.create(context)

            val result =
                credentialManager.getCredential(
                    request = request,
                    context = context
                )

            val credential =
                result.credential

            val googleIdToken =
                credential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")

            val firebaseCredential =
                GoogleAuthProvider.getCredential(
                    googleIdToken,
                    null
                )

            auth.signInWithCredential(firebaseCredential)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun signInAnonymously(): Result<Unit> {

        return try {

            auth.signInAnonymously().await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}