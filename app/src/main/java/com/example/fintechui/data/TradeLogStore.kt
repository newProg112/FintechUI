package com.example.fintechui.data

import android.os.Build
import androidx.annotation.RequiresApi

object TradeLogStore {
    private val _items = mutableListOf<String>()
    val items: List<String> get() = _items

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun add(entry: String) {
        _items.add(0, entry) // newest on top
        if (_items.size > 50) _items.removeLast()
    }
}