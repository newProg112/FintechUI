package com.example.fintechui.ui.navigation

object Routes {
    const val PORTFOLIO = "portfolio"
    const val WATCHLIST = "watchlist"
    const val ACTIVITY = "activity"
    const val GALLERY = "gallery"

    const val ASSET_SYMBOL_ARG = "symbol"
    const val ASSET = "asset/{$ASSET_SYMBOL_ARG}"

    fun asset(symbol: String) = "asset/$symbol"

    const val TRADE_SYMBOL_ARG = "symbol"
    const val TRADE = "trade/{$TRADE_SYMBOL_ARG}"
    fun trade(symbol: String) = "trade/$symbol"

    const val CONFIRM_SYMBOL_ARG = "symbol"
    const val CONFIRM_SIDE_ARG = "side"
    const val CONFIRM_AMOUNT_ARG = "amount"

    const val CONFIRM = "confirm/{$CONFIRM_SYMBOL_ARG}/{$CONFIRM_SIDE_ARG}/{$CONFIRM_AMOUNT_ARG}"
    fun confirm(symbol: String, side: String, amount: String) = "confirm/$symbol/$side/$amount"

    const val SUCCESS_SYMBOL_ARG = "symbol"
    const val SUCCESS_SIDE_ARG = "side"
    const val SUCCESS_AMOUNT_ARG = "amount"

    const val SUCCESS = "success/{$SUCCESS_SYMBOL_ARG}/{$SUCCESS_SIDE_ARG}/{$SUCCESS_AMOUNT_ARG}"
    fun success(symbol: String, side: String, amount: String) = "success/$symbol/$side/$amount"
}