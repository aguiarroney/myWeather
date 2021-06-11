package com.example.capstone.util

import androidx.databinding.InverseMethod

object Converter {

    @JvmStatic
    fun stringToInt(s: String): Int {
        if (s.isEmpty()) return 0
        else return s.toInt()
    }

    @JvmStatic
    @InverseMethod("stringToInt")
    fun intToString(d: Int): String? {
        if (d == 0) return null
        else return d.toString()
    }
}