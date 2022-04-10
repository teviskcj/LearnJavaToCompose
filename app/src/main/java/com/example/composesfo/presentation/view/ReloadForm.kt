package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun ReloadFormScreen(
    navController: NavController
) {
    val nameText = remember { mutableStateOf("") }
    val cardNumber = remember { mutableStateOf("") }
    val expiryNumber = remember { mutableStateOf("") }
    val cvcNumber = remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            item {
                OutlinedTextField(
                    value = nameText.value,
                    onValueChange = { nameText.value = it },
                    label = { stringResource(id = R.string.card_holder_name) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = cardNumber.value,
                    onValueChange = { cardNumber.value = it },
                    label = { stringResource(id = R.string.card_holder_number) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = expiryNumber.value,
                        onValueChange = { expiryNumber.value = it },
                        label = { stringResource(id = R.string.expiry_date) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                        modifier = Modifier.weight(1f)
                            .padding(end = 8.dp)
                    )
                    OutlinedTextField(
                        value = cvcNumber.value,
                        onValueChange = { cvcNumber.value = it },
                        label = { stringResource(id = R.string.cvc) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                        modifier = Modifier.weight(1f)
                            .padding(end = 8.dp)
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        navController.navigate(Screen.WalletScreen.route) {
                            navController.backQueue.clear()
                        }
                              },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Confirm",
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReloadFormPreview() {
    ReloadFormScreen(navController = rememberNavController())
}