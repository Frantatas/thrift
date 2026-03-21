package com.example.thrift.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * FirebaseAuthHelper provides a singleton access point to Firebase Authentication.
 * Encapsulates Firebase Auth SDK operations for user authentication.
 */
class FirebaseAuthHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Get the currently authenticated user.
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    /**
     * Get the UID of the currently authenticated user.
     */
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    /**
     * Register a new user with email and password.
     * @param email User's email address
     * @param password User's password
     * @return FirebaseUser if successful, null otherwise
     */
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Login a user with email and password.
     * @param email User's email address
     * @param password User's password
     * @return FirebaseUser if successful, null otherwise
     */
    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Send a password reset email to the user.
     * @param email User's email address
     * @return true if successful, false otherwise
     */
    suspend fun sendPasswordResetEmail(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Sign out the current user.
     */
    fun signOutUser() {
        auth.signOut()
    }

    /**
     * Check if a user is currently authenticated.
     */
    fun isUserAuthenticated(): Boolean = auth.currentUser != null
}

object FirebaseAuthHelperInstance {
    val instance: FirebaseAuthHelper by lazy { FirebaseAuthHelper() }
}

