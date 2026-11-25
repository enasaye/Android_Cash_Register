package com.example.cashregister.DataLayer

import java.util.Date

data class Purchase(
    val id: Int,
    val productName: String,
    val quantity: Int,
    val priceInCents: Int,
    val totalInCents: Int,
    val date: Date = Date() // time of purchase
)
