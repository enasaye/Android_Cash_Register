package com.example.cashregister.UILayer.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.CashRegisterViewModel

@Composable
fun RestockScreen(
    viewModel: CashRegisterViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.repository.products.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val enteredQuantity by viewModel.enteredQuantity.collectAsState()

    val keypadRows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("C", "0")
    )

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // TOP HEADER
        Text(
            text = "Restock: ${selectedProduct?.name ?: "Select a product"}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        // KEYPAD
        for (digits in keypadRows) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (digit in digits) {
                    Button(onClick = {
                        if (digit == "C") viewModel.clearQuantity() else viewModel.appendQuantity(digit)
                    }) {
                        Text(digit)
                    }
                }
            }
        }

        // QUANTITY & ACTION BUTTON
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quantity to Add: ${if (enteredQuantity.isEmpty()) "—" else enteredQuantity}",
                color = Color(0xFF87838D)
            )

            Button(onClick = {
                val message = viewModel.onRestockClicked()
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // PRODUCT LIST
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(products, key = { _, product -> product.id }) { index, product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectProduct(product) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = product.name, fontWeight = FontWeight.Bold)
                    Text(text = "${product.stockQuantity} in stock")
                }
                if (index < products.lastIndex) HorizontalDivider()
            }
        }
    }
}