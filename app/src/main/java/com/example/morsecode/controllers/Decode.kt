package com.example.morsecode.controllers

class Decode(private val dotDuration: Long = 100L) {

    init {
        if (dotDuration < 100L) {
            throw IllegalArgumentException("dot duration must by greater 100L")
        }
    }
}