package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.components.AppHeader
import com.zkk.wallet.ui.theme.BluePrimary
import com.zkk.wallet.ui.theme.SoftGrey
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZKKYCWalletTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnboardingScreen(
                        onInitClick = {
                            startActivity(Intent(this, AadhaarImportActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(onInitClick: () -> Unit) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 🔵 Language selector + ZK SETU gradient
        AppHeader()
        Spacer(Modifier.height(20.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { it / 8 }
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                // ⭐ Hero Heading
                Text(
                    text = "Welcome to Zero-Document KYC",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "A secure, private identity wallet right on your device.",
                    fontSize = 15.sp,
                    color = SoftGrey
                )

                Spacer(Modifier.height(32.dp))

                // ⭐ Onboarding Pointers

                OnboardingPoint(
                    title = "Verify offline",
                    subtitle = "Aadhaar is verified on your phone — no server upload."
                )

                Spacer(Modifier.height(20.dp))

                OnboardingPoint(
                    title = "No document sharing",
                    subtitle = "Banks receive mathematical proofs, never your identity."
                )

                Spacer(Modifier.height(20.dp))

                OnboardingPoint(
                    title = "Full control",
                    subtitle = "Your credential stays encrypted locally at all times."
                )

                Spacer(Modifier.height(20.dp))

                // ⭐ NEW POINTER #4
                OnboardingPoint(
                    title = "Tamper detection",
                    subtitle = "Your device checks cloud anchors to block cloned or modified credentials."
                )

                Spacer(Modifier.height(20.dp))

                // ⭐ NEW POINTER #5
                OnboardingPoint(
                    title = "Secure recovery",
                    subtitle = "Restore using OTP + device attestation without exposing any identity data."
                )

                Spacer(Modifier.height(50.dp))

                // ⭐ Get Started Button
                Button(
                    onClick = onInitClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Initialize Secure Wallet", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun OnboardingPoint(title: String, subtitle: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = BluePrimary,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = SoftGrey
            )
        }
    }
}
