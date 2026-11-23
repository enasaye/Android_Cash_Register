package com.example.cashregister.UILayer.Screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.CashRegisterViewModel
import com.example.cashregister.Product
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun HomeScreen(
    viewModel: CashRegisterViewModel,
    onManagerClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    val products by viewModel.repository.products.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val enteredQuantity by viewModel.enteredQuantity.collectAsState()
    // Calculate total price to display
    val quantityInt = enteredQuantity.toIntOrNull() ?: 0
    val currency = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val context = LocalContext.current
    val totalInCents = (selectedProduct?.priceInCents ?: 0) * quantityInt
    val formattedTotal = currency.format(totalInCents / 100.0)

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedProduct != null)
                    "Select Product: ${selectedProduct!!.name}" else "Select Product",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onManagerClicked) { Text("Manager") }
        }
        Text(
            text = "Total amount: $formattedTotal",
            color = Color(0xFF87838D),
            )

        for (digits in keypadRows) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (digit in digits) {
                    Button(onClick = {
                        if (digit == "C") {
                            viewModel.clearQuantity()
                        } else {
                            viewModel.appendQuantity(digit)
                        }
                    }) {
                        Text(digit)
                    }
                }
            }
        }

        Text(
            text = "Quantity: ${if (enteredQuantity.isEmpty()) "—" else enteredQuantity}",
            color = Color(0xFF87838D),
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val message = viewModel.onBuyClicked()
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }) { Text("Buy") }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(products, key = { _, product -> product.id }) { index, product ->
                val price = currency.format(product.priceInCents / 100.0)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectProduct(product) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "$price each")
                    }

                    Text(text = "${product.stockQuantity} in stock")
                }

                if (index < products.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}