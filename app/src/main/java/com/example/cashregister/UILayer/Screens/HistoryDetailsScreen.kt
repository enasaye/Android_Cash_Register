package com.example.cashregister.UILayer.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.DataLayer.Purchase
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistoryDetailsScreen(
    purchase: Purchase,
    modifier: Modifier = Modifier
) {
    val currency = NumberFormat.getCurrencyInstance(Locale.CANADA)
    val total = currency.format(purchase.totalInCents / 100.0)
    val unitPrice = currency.format(purchase.priceInCents / 100.0)

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Purchase Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Product: ${purchase.productName}", fontSize = 18.sp)
        Text(text = "Price: $unitPrice", fontSize = 18.sp)
        Text(text = "Quantity: ${purchase.quantity}", fontSize = 18.sp)
        Text(text = "Total: $total", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // This is the specific requirement from the assignment:
        Text(text = "Purchase Date: \n${purchase.date}", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
    }
}