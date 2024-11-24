package com.example.morsecode.controllers

import kotlinx.coroutines.delay
import android.hardware.camera2.CameraManager

class Flashlight(private val cameraManager: CameraManager) {

    suspend fun play(cameraId: String, durations: Array<Long>) {
        for ((index, duration) in durations.withIndex()) {
            if (index % 2 == 0) {
                toggle(cameraId, true)
                delay(duration)
                toggle(cameraId, false)
            } else {
                delay(duration)
            }
        }
    }

    fun toggle(cameraId: String, state: Boolean) {
        try {
            cameraManager.setTorchMode(cameraId, state);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}