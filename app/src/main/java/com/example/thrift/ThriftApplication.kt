package com.example.thrift

import android.app.Application
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Custom Application class for Thrift app.
 * Initializes Firebase collections on first app run.
 */
class ThriftApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeFirebase()
    }

    private fun initializeFirebase() {
        // Use a coroutine to initialize Firebase asynchronously
        val prefs = getSharedPreferences("firebase_init", MODE_PRIVATE)
        val isInitialized = prefs.getBoolean("collections_initialized", false)

        if (!isInitialized) {
            // Launch a coroutine in the background
            Thread {
                try {
                    kotlinx.coroutines.runBlocking {
                        FirebaseInitializer.initializeFirestore(this@ThriftApplication)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }
}

