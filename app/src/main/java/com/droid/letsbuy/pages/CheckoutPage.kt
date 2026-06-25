package com.droid.letsbuy.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.TopBar
import com.droid.letsbuy.viewmodel.CheckOutViewModel

@Composable
fun CheckoutPage(
    modifier: Modifier = Modifier,
    viewModel: CheckOutViewModel = viewModel()
) {
    val userModel by viewModel.userModel.collectAsState()
    val subTotal by viewModel.subTotal.collectAsState()
    val discount by viewModel.discount.collectAsState()
    val tax by viewModel.tax.collectAsState()
    val total by viewModel.total.collectAsState()

    Scaffold(
        topBar = {
            TopBar("")
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() - 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState()),
        ) {
            Text("Checkout", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Deliver to : ", fontWeight = FontWeight.SemiBold)
                Text(userModel.address)
            }
            Spacer(Modifier.height(16.dp))

            HorizontalDivider()
            Spacer(Modifier.height(16.dp))
            RowCheckout("Subtotal", subTotal)
            Spacer(Modifier.height(8.dp))
            RowCheckout("Discount", discount)
            Spacer(Modifier.height(8.dp))
            RowCheckout("Tax", tax)
            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            RowCheckout("Total", total)

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {}
            ) {
                Text("Pay Now")
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RowCheckout(title: String, value: Float) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("$ ${String.format("%.2f", value)}", fontSize = 20.sp)
    }

}