package com.example.composesfo.presentation.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.domain.model.Wallet
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun WalletScreen(
    navController: NavController,
    viewModel: WalletViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            state.wallet?.let {
                CurrentUserState.currentAmount = it.walletAmount
                ShowBalance(wallet = it)
            }
            Spacer(modifier = Modifier.weight(1f))
            ShowReloadMethod(navController = navController)
        }
    }
}

@Composable
fun ShowBalance(wallet: Wallet) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            text = stringResource(R.string.balance),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Text(
            text = "${wallet.walletAmount}.00",
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp
        )

        Text(
            text = "MYR",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}

@Composable
fun ShowReloadMethod(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.select_reload_amount),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                    onClick = { /*TODO*/ }
            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_qr_code),
                contentDescription = stringResource(R.string.qr_code),
                modifier = Modifier.size(48.dp)
            )
        }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr_code_scanner),
                    contentDescription = stringResource(R.string.qr_scanner),
                    modifier = Modifier.size(48.dp)
                )
            }
        }




        Button(
            onClick = {
                CurrentUserState.reloadAmount = 10
                navController.navigate(route = Screen.ReloadFormScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "10.00 MYR",
                fontSize = 22.sp
            )
        }

        Button(
            onClick = {
                CurrentUserState.reloadAmount = 20
                navController.navigate(route = Screen.ReloadFormScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "20.00 MYR",
                fontSize = 22.sp
            )
        }

        Button(
            onClick = {
                CurrentUserState.reloadAmount = 50
                navController.navigate(route = Screen.ReloadFormScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "50.00 MYR",
                fontSize = 22.sp
            )
        }

        Button(
            onClick = {
                CurrentUserState.reloadAmount = 100
                navController.navigate(route = Screen.ReloadFormScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "100.00 MYR",
                fontSize = 22.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WalletScreenPreview() {
    WalletScreen(navController = rememberNavController())
}