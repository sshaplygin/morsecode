package com.example.morsecode.menu

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.morsecode.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SOSFragment: Fragment(R.layout.fragment_sos) {
    private var isFlashlightOn = false

    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var job: Job? = null

    private fun toggleFlashlight(cameraId: String, state: Boolean) {
        try {
            cameraManager.setTorchMode(cameraId, state);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sos, container, false)
        val button: Button = view.findViewById(R.id.toggleSOSButton)

        cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager;
        cameraId = cameraManager.cameraIdList.getOrNull(0);

        button.setOnClickListener {
            if (isFlashlightOn) {
                isFlashlightOn = false
                job?.cancel()

                cameraId?.let {
                    toggleFlashlight(it, false)
                }

                return@setOnClickListener
            }

            isFlashlightOn = true

            job = cameraId?.let {
                lifecycleScope.launch {
                    sendSOS(it)
                }
            }
        }

        return view
    }

    private suspend fun sendSOS(cameraId: String) {
        val dotDuration = 300L
        val dashDuration = dotDuration * 3
        val elementGap = dotDuration
        val letterGap = dotDuration * 3

        val sosPattern = listOf(
            dotDuration, elementGap, dotDuration, elementGap, dotDuration, letterGap, // ...
            dashDuration, elementGap, dashDuration, elementGap, dashDuration, letterGap, // ---
            dotDuration, elementGap, dotDuration, elementGap, dotDuration // ...
        )

        for ((index, duration) in sosPattern.withIndex()) {
            if (index % 2 == 0) {
                toggleFlashlight(cameraId, true)
                delay(duration)
                toggleFlashlight(cameraId, false)
            } else {
                delay(duration)
            }
        }
    }
}