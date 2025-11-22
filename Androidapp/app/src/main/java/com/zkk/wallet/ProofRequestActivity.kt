package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.zkk.wallet.ui.theme.SoftGrey
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme

class ProofRequestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZKKYCWalletTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProofRequestScreen(
                        onAllow = {
                            startActivity(Intent(this, ProofResultActivity::class.java))
                        },
                        onDeny = { finish() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProofRequestScreen(
    onAllow: () -> Unit,
    onDeny: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("zkk", android.content.Context.MODE_PRIVATE)

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val requestText = """
        This institution is requesting a Zero-Knowledge proof:

        • Verifier: State Bank of India  
        • Purpose: Age Verification (18+)  
        • Session Nonce: XJ3921AB  
        
        No personal data will be shared.  
        Only mathematical proof is revealed.
    """.trimIndent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        AppHeader()

        Spacer(Modifier.height(20.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(450)) + slideInVertically(tween(500)) { it / 8 }
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Age Verification Request",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )

                Spacer(Modifier.height(20.dp))

                // THE MAIN CARD
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    tonalElevation = 8.dp,
                    shadowElevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(26.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        // Icon Row
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = "A verifier is requesting proof",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(Modifier.height(22.dp))

                        Text(
                            text = requestText,
                            fontSize = 14.sp,
                            color = SoftGrey,
                            lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(16.dp))

                        // Mini assurance badge
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Verified,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Powered by Zero-Knowledge Proofs",
                                fontSize = 13.sp,
                                color = BluePrimary
                            )
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                // ALLOW button
                Button(
                    onClick = {
                        var credential = prefs.getString("credential", null)

                        if (credential == null) {
                            credential = """
                                {
                                  "credentialId": "cred-001",
                                  "attributesHash": "FAKE_HASH_123456",
                                  "version": 1
                                }
                            """.trimIndent()
                            prefs.edit().putString("credential", credential).apply()
                        }

                        val proof = """
                            {
                              "credentialId": "cred-001",
                              "proofType": "age_over_18",
                              "nonce": "XJ3921AB",
                              "zkProofBlob": "ABCD1234FAKEPROOF"
                            }
                        """.trimIndent()

                        prefs.edit().putString("zkProof", proof).apply()

                        onAllow()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(55.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Allow", fontSize = 18.sp)
                }

                Spacer(Modifier.height(16.dp))

                // DENY button
                OutlinedButton(
                    onClick = onDeny,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(55.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Deny", fontSize = 18.sp)
                }
            }
        }
    }
}
