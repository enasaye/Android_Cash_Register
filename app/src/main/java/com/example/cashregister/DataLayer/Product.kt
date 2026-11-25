package com.example.cashregister.DataLayer

data class Product(
    val id: Int,
    val name: String,
    val priceInCents: Int,
    val stockQuantity: Int
)

val sampleProducts = listOf(
    Product(id = 1, name = "Sweater", priceInCents = 2999, stockQuantity = 10),
    Product(id = 2, name = "Scarf", priceInCents = 1499, stockQuantity = 15),
    Product(id = 3, name = "Socks", priceInCents = 599, stockQuantity = 25)
)