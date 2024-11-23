package com.example.morsecode.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.morsecode.R
import kotlinx.coroutines.Dispatchers

class DecodeFragment: Fragment(R.layout.fragment_decode) {

    private var isDecodeOn = false

    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(findViewById<PreviewView>(R.id.previewView).surfaceProvider)
//            }
//
//            val imageAnalyzer = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//                .also {
//                    it.setAnalyzer(Dispatchers.Default.asExecutor()) { imageProxy ->
//                        analyzeImage(imageProxy)
//                    }
//                }
//
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageAnalyzer
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }, ContextCompat.getMainExecutor(this))
    }

    private fun analyzeImage(imageProxy: ImageProxy) {
        // Извлечение яркости
        val buffer = imageProxy.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        // Средняя яркость
        val brightness = data.map { it.toInt() and 0xFF }.average()

        // Установите порог яркости для определения включения фонарика
        if (brightness > 200) {
            println("Фонарик, вероятно, включён!")
//            runOnUiThread {
//                println("Фонарик, вероятно, включён!")
//            }
        }

        imageProxy.close()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_decode, container, false)

        val button: Button = view.findViewById(R.id.toggleDecodeButton)

        button.setOnClickListener {
            if (isDecodeOn) {
                startCamera()
            }

            // TODO: stop camera
        }

        return view
    }
}