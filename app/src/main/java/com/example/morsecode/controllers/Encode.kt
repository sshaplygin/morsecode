package com.example.morsecode.controllers

class Encode(private val dotDuration: Long = 100L) {

    init {
        if (dotDuration < 100L) {
            throw IllegalArgumentException("dot duration must by greater 100L")
        }
    }

    private val alphabet: HashMap<Char, Array<Char>> = hashMapOf(
        'A' to arrayOf('.', '-'),
        'B' to arrayOf('-', '.', '.', '.'),
        'C' to arrayOf('-', '.', '-', '.'),
        'D' to arrayOf('-', '.', '.'),
        'E' to arrayOf('.'),
        'F' to arrayOf('.', '.', '-', '.'),
        'G' to arrayOf('-', '-', '.'),
        'H' to arrayOf('.', '.', '.', '.'),
        'I' to arrayOf('.', '.'),
        'J' to arrayOf('.', '-', '-', '-'),
        'K' to arrayOf('-', '.', '-'),
        'L' to arrayOf('.', '-', '.', '.'),
        'M' to arrayOf('-', '-'),
        'N' to arrayOf('-', '.'),
        'O' to arrayOf('-', '-', '-'),
        'P' to arrayOf('.', '-', '-', '.'),
        'Q' to arrayOf('-', '-', '.', '-'),
        'R' to arrayOf('.', '-', '.'),
        'S' to arrayOf('.', '.', '.'),
        'T' to arrayOf('-'),
        'U' to arrayOf('.', '.', '-'),
        'V' to arrayOf('.', '.', '.', '-'),
        'W' to arrayOf('.', '-', '-'),
        'X' to arrayOf('-', '.', '.', '-'),
        'Y' to arrayOf('-', '.', '-', '-'),
        'Z' to arrayOf('-', '-', '.', '.'),
        '0' to arrayOf('-', '-', '-', '-', '-'),
        '1' to arrayOf('.', '-', '-', '-', '-'),
        '2' to arrayOf('.', '.', '-', '-', '-'),
        '3' to arrayOf('.', '.', '.', '-', '-'),
        '4' to arrayOf('.', '.', '.', '.', '-'),
        '5' to arrayOf('.', '.', '.', '.', '.'),
        '6' to arrayOf('-', '.', '.', '.', '.'),
        '7' to arrayOf('-', '-', '.', '.', '.'),
        '8' to arrayOf('-', '-', '-', '.', '.'),
        '9' to arrayOf('-', '-', '-', '-', '.'),
    )

    fun encodeWord(word: String): Array<Long> {
        val res = mutableListOf<Long>();
        for (char in word) {
            val arr = alphabet[char.uppercaseChar()] ?: continue
            for (s in arr) {
                var duration = dotDuration
                if (s == '-') {
                    duration *= 3
                }

                res.add(duration);
            }
        }

        return res.toTypedArray();
    }
}