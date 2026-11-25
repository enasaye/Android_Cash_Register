package com.example.cashregister.DataLayer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoreRepository{

    // 1. Private mutable state holding the list of Products
    private val _products = MutableStateFlow<List<Product>>(sampleProducts)
    // 2. Public read-only state for the ViewModel to observe
    val products: StateFlow<List<Product>> = _products.asStateFlow()
    // 3. Private mutable state holding the list of Purchases (starts empty)
    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    // 4. Public read-only state for Purchases
    val purchases: StateFlow<List<Purchase>> = _purchases.asStateFlow()

    fun processPurchase(product: Product, quantityToBuy: Int): Boolean {

        val currentProducts = _products.value.toMutableList()

        val index = currentProducts.indexOfFirst { it.id == product.id }
        if (index == -1)
            return false // (product not found)
        val currentProduct = currentProducts[index]

        // enough stock check.
        if (currentProduct.stockQuantity >= quantityToBuy){
            val updateProduct = currentProduct.copy(
                stockQuantity = currentProduct.stockQuantity - quantityToBuy
            )
            currentProducts[index] = updateProduct

            _products.value = currentProducts

            // purchase record
            val totalPrice = quantityToBuy * currentProduct.priceInCents
            val newPurchase = Purchase(
                id = _purchases.value.size + 1,
                productName = currentProduct.name,
                quantity = quantityToBuy,
                priceInCents = currentProduct.priceInCents,
                totalInCents = totalPrice
            )

            // add to purchase history
            _purchases.value = _purchases.value + newPurchase

            return true
        }

        return false // failed due to not enough stock
    }

    // Adds stock to a specific product
    fun restockProduct(product: Product, quantityToAdd: Int) {
        val currentProducts = _products.value.toMutableList()
        val index = currentProducts.indexOfFirst { it.id == product.id }

        if (index != -1) {
            val currentProduct = currentProducts[index]
            // Add the new quantity to the existing stock
            val updatedProduct = currentProduct.copy(
                stockQuantity = currentProduct.stockQuantity + quantityToAdd
            )
            currentProducts[index] = updatedProduct
            _products.value = currentProducts
        }
    }

}

