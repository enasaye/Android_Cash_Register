package com.example.cashregister.UILayer.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManagerScreen(
    onHistoryClicked: () -> Unit,
    onRestockClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centers the buttons on the screen
    ) {
        Button(
            onClick = onHistoryClicked,
            modifier = Modifier.fillMaxWidth(0.6f).padding(bottom = 16.dp)
        ) {
            Text("History")
        }

        Button(
            onClick = onRestockClicked,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Restock")
        }
    }
}