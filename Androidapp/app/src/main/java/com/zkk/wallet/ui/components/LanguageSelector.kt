package com.zkk.wallet.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.theme.SoftGrey
import com.zkk.wallet.ui.theme.SoftWhite
import com.zkk.wallet.ui.theme.BluePrimary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun LanguageSelector(
    modifier: Modifier = Modifier,
    languages: List<String> = listOf("English", "हिन्दी", "मराठी", "বাংলা", "ગુજરાતી"),
    onSelect: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var current by remember { mutableStateOf(languages.first()) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = SoftWhite,
        modifier = modifier
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 6.dp)
                .clickable { expanded = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = current, fontSize = 14.sp, color = BluePrimary)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Lang",
                tint = BluePrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { lang ->
                DropdownMenuItem(
                    text = { Text(lang) },
                    onClick = {
                        current = lang
                        expanded = false
                        onSelect(lang)
                    }
                )
            }
        }
    }
}
