package com.example.composesfo.presentation.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.UiEvent
import com.example.composesfo.data.remote.dto.WalletDto
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun ReloadFormScreen(
    navController: NavController,
    viewModel: WalletViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TopBarTitle(textOne = "Reload", textTwo = "Form")
                Spacer(modifier = Modifier.padding(10.dp))
                ReloadTextField(
                    name = viewModel.name,
                    cardNumber = viewModel.cardNumber,
                    expiryDate = viewModel.expiryDate,
                    cvcNumber = viewModel.cvcNumber,
                    onNameChange = viewModel::onNameChange,
                    onCardNumberChange = viewModel::onCardNumberChange,
                    onExpiryDateChange = viewModel::onExpiryDateChange,
                    onCVCNumberChange = viewModel::onCVCNumberChange
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val walletDto = WalletDto(
                            phone = CurrentUserState.userId,
                            walletAmount = viewModel.getReloadedAmount(CurrentUserState.currentAmount, CurrentUserState.reloadAmount)
                        )
                        if (viewModel.checkNullField(CurrentUserState.userId,walletDto)) {
                            if (viewModel.isReloadSuccessful(state.error)) {
                                navController.navigate(Screen.WalletScreen.route) {
                                    navController.backQueue.clear()
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AllButton),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            bottom = 34.dp
                        )
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Confirm Reload",
                        color = white,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReloadTextField(
    name: String,
    cardNumber: String,
    expiryDate: String,
    cvcNumber: String,
    onNameChange: (String) -> Unit,
    onCardNumberChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onCVCNumberChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TextFieldWithNoIcon(
            text = name,
            onTextChange = onNameChange,
            label = stringResource(id = R.string.card_holder_name),
            modifier = Modifier.fillMaxWidth()
        )

        TextFieldWithNoIcon(
            text = cardNumber,
            onTextChange = onCardNumberChange,
            label = stringResource(id = R.string.card_holder_number),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextFieldWithNoIcon(
                text = expiryDate,
                onTextChange = onExpiryDateChange,
                label = stringResource(id = R.string.expiry_date),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f)
            )

            TextFieldWithNoIcon(
                text = cvcNumber,
                onTextChange = onCVCNumberChange,
                label = stringResource(id = R.string.cvc),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(0.5f)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ReloadFormPreview() {
    ReloadFormScreen(navController = rememberNavController())
}