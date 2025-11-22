//LoadingScreenActivity.kt
package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme
import kotlinx.coroutines.delay

class LoadingScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZKKYCWalletTheme {

                Surface(modifier = Modifier.fillMaxSize()) {

                    LoadingScreen()

                    // MOVE FORWARD AFTER DELAY
                    LaunchedEffect(true) {
                        delay(1000)

                        val prefs = getSharedPreferences("zkk", MODE_PRIVATE)
                        val pinSaved = prefs.getString("walletPin", null)

                        if (pinSaved == null) {
                            startActivity(Intent(this@LoadingScreenActivity, CreatePinActivity::class.java))
                        } else {
                            startActivity(Intent(this@LoadingScreenActivity, EnterPinActivity::class.java))
                        }

                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(20.dp))
        Text("Loading wallet...", fontSize = 18.sp)
    }
}
