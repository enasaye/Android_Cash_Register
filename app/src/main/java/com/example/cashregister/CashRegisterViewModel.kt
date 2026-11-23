package com.example.cashregister

import androidx.lifecycle.ViewModel
import com.example.cashregister.DataLayer.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CashRegisterViewModel : ViewModel() {

    // 1. The Repository ( data source)
    val repository = StoreRepository()

    // 2. State for the currently selected product (starts as null)
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // 3. State for the quantity typed on the keypad (starts as an empty string)
    private val _enteredQuantity = MutableStateFlow("")
    val enteredQuantity: StateFlow<String> = _enteredQuantity.asStateFlow()

    // --- USER ACTIONS ---

    // Called when the user taps a product in the list
    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    // Called when the user taps a number on the keypad (e.g., "1", "2")
    fun appendQuantity(digit: String) {
        _enteredQuantity.value = _enteredQuantity.value + digit
    }

    // Called when the user taps "C" on the keypad
    fun clearQuantity() {
        _enteredQuantity.value = ""
    }

    // Called when the user clicks the "Buy" button
    // Returns a String message to show in a Toast
    fun onBuyClicked(): String {
        val currentProduct = _selectedProduct.value
        val quantityStr = _enteredQuantity.value

        // 1. Validation check: Did they select a product and enter a quantity?
        if (currentProduct == null || quantityStr.isEmpty()) {
            return "All fields are required!"
        }

        // Convert the string quantity to a real Int number
        val quantityToBuy = quantityStr.toIntOrNull() ?: 0

        // 2. Try to process the purchase in the repository
        val success = repository.processPurchase(currentProduct, quantityToBuy)

        if (success) {
            // 3. Reset the UI for the next customer
            _selectedProduct.value = null
            _enteredQuantity.value = ""
            return "Purchase Successful!"
        } else {
            return "Not enough quantity in stock!"
        }
    }

    // Called when Manager clicks "Add" on the Restock screen
    fun onRestockClicked(): String {
        val currentProduct = _selectedProduct.value
        val quantityStr = _enteredQuantity.value

        if (currentProduct == null || quantityStr.isEmpty()) {
            return "All fields are required!"
        }

        val quantityToAdd = quantityStr.toIntOrNull() ?: 0
        repository.restockProduct(currentProduct, quantityToAdd)

        // Reset UI
        _selectedProduct.value = null
        _enteredQuantity.value = ""
        return "Restock Successful!"
    }
}