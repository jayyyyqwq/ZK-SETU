package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.components.AppHeader
import com.zkk.wallet.ui.theme.BluePrimary
import com.zkk.wallet.ui.theme.SoftGrey
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme

class EnterPinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZKKYCWalletTheme {

                val prefs = getSharedPreferences("zkk", MODE_PRIVATE)
                val savedPin = prefs.getString("walletPin", null)

                Surface(modifier = Modifier.fillMaxSize()) {
                    EnterPinScreen(
                        savedPin = savedPin,
                        onUnlock = {
                            startActivity(Intent(this, OnboardingActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EnterPinScreen(
    savedPin: String?,
    onUnlock: () -> Unit
) {
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    // Trigger fade-in animation
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 🔵 Language dropdown + clean top spacing
        AppHeader()

        Spacer(Modifier.height(30.dp))

        // 🔥 Animated content
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { it / 8 },
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // Subtitle
                Text(
                    text = "Welcome to",
                    fontSize = 18.sp,
                    color = SoftGrey
                )

                // App Name
                Text(
                    text = "Zero-Document KYC",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )

                Spacer(Modifier.height(40.dp))

                // PIN Input Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    tonalElevation = 6.dp,
                    shadowElevation = 6.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    Column(
                        Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Unlock Wallet",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(Modifier.height(14.dp))

                        OutlinedTextField(
                            value = pin,
                            onValueChange = { if (it.length <= 6) pin = it },
                            label = { Text("Enter PIN") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (error.isNotEmpty()) {
                            Spacer(Modifier.height(10.dp))
                            Text(error, color = Color.Red, fontSize = 14.sp)
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                // Unlock Button (Sexy pill button)
                Button(
                    onClick = {
                        if (pin == savedPin) onUnlock()
                        else error = "Incorrect PIN"
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Unlock", fontSize = 18.sp)
                }
            }
        }
    }
}
