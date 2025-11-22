package com.zkk.wallet

import androidx.activity.compose.rememberLauncherForActivityResult
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zkk.wallet.ui.theme.ZKKYCWalletTheme
import java.io.InputStream
import java.security.MessageDigest
import android.content.Intent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.edit
import com.zkk.wallet.ui.theme.BluePrimary
import com.zkk.wallet.ui.theme.SoftGrey
import com.zkk.wallet.ui.components.AppHeader   // ⭐ correct import

class AadhaarImportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZKKYCWalletTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AadhaarImportScreen(
                        onProceed = {
                            startActivity(Intent(this, ProofRequestActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AadhaarImportScreen(onProceed: () -> Unit) {

    val context = LocalContext.current

    var fileName by remember { mutableStateOf("No file selected") }
    var resultText by remember { mutableStateOf("Waiting for Aadhaar file...") }
    var canProceed by remember { mutableStateOf(false) }

    val prefs = context.getSharedPreferences("zkk", Context.MODE_PRIVATE)

    // File picker launcher
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {

                // Validate type
                val name = uri.lastPathSegment ?: ""
                if (!name.endsWith(".xml") && !name.endsWith(".zip")) {
                    resultText = "Invalid Aadhaar file. Please select the XML or ZIP from UIDAI."
                    return@rememberLauncherForActivityResult
                }

                fileName = name

                val sha256 = computeSha256FromUri(context, uri)

                if (sha256 != null) {
                    resultText = "Aadhaar file imported.\nHash: ${sha256.take(12)}..."
                    prefs.edit { putString("aadhaarHash", sha256) }
                    canProceed = true
                } else {
                    resultText = "Cannot read file."
                }
            } else {
                resultText = "No file selected."
            }
        }
    )

    // Auto-forward once ready
    LaunchedEffect(canProceed) {
        if (canProceed) {
            kotlinx.coroutines.delay(600)
            onProceed()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header
        AppHeader()
        Spacer(Modifier.height(20.dp))

        Text(
            text = "Zero-Document KYC Wallet",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = BluePrimary
        )

        Text(
            text = "Your identity stays with you.",
            fontSize = 14.sp,
            color = SoftGrey
        )

        Spacer(Modifier.height(28.dp))

        Surface(
            shape = RoundedCornerShape(22.dp),
            shadowElevation = 8.dp,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Import Aadhaar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(22.dp))

                // ⭐ OPTION 1 — Mock QR Scan
                Button(
                    onClick = {
                        // Fake QR data
                        val mockHash = "mock_qr_hash_12345"

                        prefs.edit {
                            putString("aadhaarHash", mockHash)
                        }

                        resultText = "QR scanned successfully (mock)."
                        canProceed = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Scan Aadhaar QR (Mock)")
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    "OR",
                    fontSize = 14.sp,
                    color = SoftGrey
                )

                Spacer(Modifier.height(18.dp))

                // ⭐ OPTION 2 — Upload Aadhaar file
                Button(
                    onClick = {
                        pickFileLauncher.launch(
                            arrayOf("application/xml", "application/zip")
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Upload Aadhaar XML / ZIP")
                }

                Spacer(Modifier.height(18.dp))

                Text("File: $fileName", fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))
                Text(resultText, color = SoftGrey, fontSize = 13.sp)
            }
        }
    }
}

// ⭐ NO AppHeader HERE. Removed completely.

fun computeSha256FromUri(context: Context, uri: Uri): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val md = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(8192)
        var read: Int

        while (inputStream!!.read(buffer).also { read = it } > 0) {
            md.update(buffer, 0, read)
        }

        inputStream.close()

        val digest = md.digest()
        digest.joinToString("") { "%02x".format(it) }

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
