//CreatePinActivity.kt
package com.zkk.wallet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme
import androidx.core.content.edit

class CreatePinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZKKYCWalletTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CreatePinScreen(
                        onPinSet = { pin ->
                            val prefs = getSharedPreferences("zkk", MODE_PRIVATE)
                            prefs.edit { putString("walletPin", pin) }

                            startActivity(Intent(this, EnterPinActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CreatePinScreen(onPinSet: (String) -> Unit) {
    var pin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Set Wallet PIN", fontSize = 24.sp)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = pin,
            onValueChange = { if (it.length <= 6) pin = it },
            label = { Text("Enter 4-6 digit PIN") }
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = { if (pin.length >= 4) onPinSet(pin) }) {
            Text("Set PIN")
        }
    }
}
