package com.example.vantat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biometricPrompt = createBiometric()
    }

    private fun createBiometric(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {

                Toast.makeText(
                    this@MainActivity,
                    "onAuthenticationError\nerrorCode: $errorCode\nerrString: $errString",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                startActivity(intent)
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(this@MainActivity, "onAuthenticationFailed",
                    Toast.LENGTH_SHORT).show()
            }
        }

        return BiometricPrompt(this,executor,callback)
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            //.setNegativeButtonText("Cancel")
            .setDeviceCredentialAllowed(true)
            //.setConfirmationRequired(false)
            .build()

    }

    fun check(v: View) {
        val createPromptInfo = createPromptInfo()
        biometricPrompt.authenticate(createPromptInfo)
    }
}
