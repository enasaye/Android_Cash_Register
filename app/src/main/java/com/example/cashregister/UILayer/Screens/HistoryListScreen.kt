package com.example.cashregister.UILayer.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.CashRegisterViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistoryListScreen(
    viewModel: CashRegisterViewModel,
    onPurchaseClicked: (Int) -> Unit, // Passes the Purchase ID back to the Navigation host
    modifier: Modifier = Modifier
) {
    val purchases by viewModel.repository.purchases.collectAsState()
    val currency = NumberFormat.getCurrencyInstance(Locale.CANADA)

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Purchase History",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (purchases.isEmpty()) {
            Text("No purchases yet!")
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(purchases, key = { _, purchase -> purchase.id }) { index, purchase ->
                    val total = currency.format(purchase.totalInCents / 100.0)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPurchaseClicked(purchase.id) }
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = purchase.productName, fontWeight = FontWeight.Bold)
                            Text(text = "Qty: ${purchase.quantity}")
                        }
                        Text(text = total, fontWeight = FontWeight.Bold)
                    }

                    if (index < purchases.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}