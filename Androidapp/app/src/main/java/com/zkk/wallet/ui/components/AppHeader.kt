package com.zkk.wallet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally  // ⭐ CENTER EVERYTHING
    ) {

        // ⭐ Centered ZK SETU with Gradient
        Text(
            text = "ZK SETU",
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .drawWithCache {
                    val gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF6F00),  // saffron
                            Color(0xFFFFFFFF),  // white
                            Color(0xFF1B5E20)   // green
                        ),
                        startX = 0f,
                        endX = size.width * 1.6f
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, alpha = 0.75f)
                    }
                }
        )

        Spacer(Modifier.height(10.dp))

        // ⭐ Centered Language Selector
        LanguageSelector()
    }
}
