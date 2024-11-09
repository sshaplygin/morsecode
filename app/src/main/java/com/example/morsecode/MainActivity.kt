package com.example.morsecode

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.morsecode.ui.theme.MorseCodeTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {

    private var isFlashlightOn = false

    private fun toggleFlashlight(context: Context) {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager;
        val cameraId = cameraManager.cameraIdList.getOrNull(0);
        if (cameraId == null) {
            Toast.makeText(context, "Устройство не поддерживает фонарик", Toast.LENGTH_SHORT).show()
            return;
        }

        try {
            cameraManager.setTorchMode(cameraId, !isFlashlightOn);
            isFlashlightOn = !isFlashlightOn;
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = "Hello there!"

        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {
            toggleFlashlight(applicationContext)
        }
//        enableEdgeToEdge()
//        setContent {
//            MorseCodeTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MorseCodeTheme {
//        Greeting("Android")
//    }
//}