package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun WalletScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = walletScreenConstraintSet()
        ) {
            Text(
                text = "Balance",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.layoutId("balanceText")
            )

            Text(
                text = "100.00",
                fontStyle = FontStyle.Italic,
                fontSize = 25.sp,
                modifier = Modifier.layoutId("amountBalance")
            )

            Text(
                text = "MYR",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.layoutId("currencyText")
            )

            Text(
                text = "Select Reload Amount",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.layoutId("reloadText")
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("qrImage")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr_code),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("qrScannerImage")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr_code_scanner),
                    contentDescription = "QR Scanner",
                    modifier = Modifier.size(48.dp)
                )
            }

            Button(
                onClick = { navController.navigate(route = Screen.ReloadFormScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("reloadTen")
            ) {
                Text(
                    text = "10.00 MYR",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { navController.navigate(route = Screen.ReloadFormScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("reloadTwenty")
            ) {
                Text(
                    text = "20.00 MYR",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { navController.navigate(route = Screen.ReloadFormScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("reloadFifty")
            ) {
                Text(
                    text = "50.00 MYR",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { navController.navigate(route = Screen.ReloadFormScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("reloadHundred")
            ) {
                Text(
                    text = "100.00 MYR",
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun walletScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val balanceText = createRefFor("balanceText")
        val amountBalance = createRefFor("amountBalance")
        val currencyText = createRefFor("currencyText")
        val reloadText = createRefFor("reloadText")
        val qrImage = createRefFor("qrImage")
        val qrScannerImage = createRefFor("qrScannerImage")
        val reloadTen = createRefFor("reloadTen")
        val reloadTwenty = createRefFor("reloadTwenty")
        val reloadFifty = createRefFor("reloadFifty")
        val reloadHundred = createRefFor("reloadHundred")
        
        constrain(balanceText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }
        
        constrain(amountBalance) {
            top.linkTo(balanceText.bottom, 20.dp)
            centerHorizontallyTo(parent)
        }
        
        constrain(currencyText) {
            top.linkTo(amountBalance.bottom, 20.dp)
            centerHorizontallyTo(parent)
        }
        
        constrain(reloadText) {
            bottom.linkTo(reloadTen.top, 20.dp)
        }
        
        constrain(qrImage) {
            bottom.linkTo(reloadText.bottom)
            end.linkTo(qrScannerImage.start)
        }

        constrain(qrScannerImage) {
            bottom.linkTo(reloadText.bottom)
            end.linkTo(parent.end)
        }

        constrain(reloadTen) {
            bottom.linkTo(reloadTwenty.top, 20.dp)
            start.linkTo(reloadHundred.start)
            end.linkTo(reloadHundred.end)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(reloadTwenty) {
            bottom.linkTo(reloadFifty.top, 20.dp)
            start.linkTo(reloadHundred.start)
            end.linkTo(reloadHundred.end)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(reloadFifty) {
            bottom.linkTo(reloadHundred.top, 20.dp)
            start.linkTo(reloadHundred.start)
            end.linkTo(reloadHundred.end)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(reloadHundred) {
            bottom.linkTo(parent.bottom, 20.dp)
            start.linkTo(parent.start, 30.dp)
            end.linkTo(parent.end, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WalletScreenPreview() {
    WalletScreen(navController = rememberNavController())
}