package com.example.thrift.repository

import com.example.thrift.data.models.User
import com.example.thrift.firebase.FirebaseAuthHelper
import com.example.thrift.firebase.FirestoreHelper

/**
 * AuthRepository abstracts authentication logic and provides a clean interface
 * for the presentation layer to interact with Firebase Auth.
 * This ensures separation of concerns and makes the code testable.
 */
class AuthRepository(
    private val authHelper: FirebaseAuthHelper = FirebaseAuthHelper(),
    private val firestoreHelper: FirestoreHelper = FirestoreHelper()
) {

    /**
     * Register a new user with email and password.
     * Also creates a user profile in Firestore.
     *
     * @param email User's email
     * @param password User's password
     * @param displayName User's display name
     * @return Result containing the user or error message
     */
    suspend fun register(
        email: String,
        password: String,
        displayName: String
    ): Result<User> {
        return try {
            val firebaseUser = authHelper.registerUser(email, password)
                ?: return Result.failure(Exception("Registration failed"))

            val user = User(
                uid = firebaseUser.uid,
                email = email,
                displayName = displayName
            )

            val userProfileCreated = firestoreHelper.setUserProfile(user)
            if (!userProfileCreated) {
                return Result.failure(Exception("Failed to create user profile"))
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login a user with email and password.
     *
     * @param email User's email
     * @param password User's password
     * @return Result containing the user or error message
     */
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val firebaseUser = authHelper.loginUser(email, password)
                ?: return Result.failure(Exception("Login failed"))

            val user = firestoreHelper.getUserProfile(firebaseUser.uid)
                ?: return Result.failure(Exception("User profile not found"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Send a password reset email.
     *
     * @param email User's email
     * @return Result indicating success or failure
     */
    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            val success = authHelper.sendPasswordResetEmail(email)
            if (success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to send password reset email"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get the currently authenticated user.
     *
     * @return User or null if not authenticated
     */
    suspend fun getCurrentUser(): User? {
        return try {
            val uid = authHelper.getCurrentUserId() ?: return null
            firestoreHelper.getUserProfile(uid)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Sign out the current user.
     */
    fun signOut() {
        authHelper.signOutUser()
    }

    /**
     * Check if user is authenticated.
     *
     * @return true if user is logged in, false otherwise
     */
    fun isUserAuthenticated(): Boolean = authHelper.isUserAuthenticated()
}

object AuthRepositoryInstance {
    val instance: AuthRepository by lazy { AuthRepository() }
}

