//MainActivity.kt
package com.zkk.wallet
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.theme.BluePrimary
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZKKYCWalletTheme {
                SplashScreen {
                    // 🔥 NEW FLOW: Go to Loading screen instead of Onboarding
                    startActivity(Intent(this, LoadingScreenActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(1800)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        // SMOOTH SCALE ANIMATION
        val scale = remember { androidx.compose.animation.core.Animatable(0.6f) }

        LaunchedEffect(true) {
            scale.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = LinearOutSlowInEasing
                )
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(140.dp)
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "ZK-KYC Wallet",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = BluePrimary
            )
        }
    }
}
