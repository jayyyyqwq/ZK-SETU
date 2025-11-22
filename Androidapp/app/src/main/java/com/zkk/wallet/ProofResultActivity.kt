package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.components.AppHeader
import com.zkk.wallet.ui.theme.BluePrimary
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme

class ProofResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZKKYCWalletTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProofResultScreen(
                        onReturnHome = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProofResultScreen(onReturnHome: () -> Unit) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("zkk", android.content.Context.MODE_PRIVATE)

    val zkProof = prefs.getString("zkProof", "No proof found")

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppHeader()

        Spacer(Modifier.height(20.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(450)) + slideInVertically(tween(600)) { it / 5 }
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(8.dp)
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Proof Verified",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Your age was verified using\nmathematical proof only.",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(Modifier.height(26.dp))

                // Animated card for proof JSON
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(600)) + expandVertically(),
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = 8.dp,
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                "Verification Details",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = zkProof ?: "No proof data",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = onReturnHome,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(55.dp),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text("Return to Home", fontSize = 18.sp)
                }
            }
        }
    }
}
