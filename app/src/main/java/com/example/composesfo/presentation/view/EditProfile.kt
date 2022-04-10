package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
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
import com.example.composesfo.presentation.ui.theme.AllButton

@Composable
fun EditProfileScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            constraintSet = editProfileConstraintSet()
        ) {
            Card(modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .layoutId("accountCard")
            ) {
                Column {
                    Text(
                        text = "Account Info",
                        fontSize = 28.sp,
                    )

                    Row {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = null,
                            tint = AllButton
                        )
                        Text(
                            text = "chianjia",
                            fontSize = 20.sp
                        )
                    }

                    Row {
                        Icon(
                            Icons.Filled.Lock,
                            contentDescription = null,
                            tint = AllButton
                        )
                        Text(
                            text = "testing",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Card(modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .layoutId("editCard")
            ) {
                Column {
                    Text(
                        text = "Edit Field",
                        fontSize = 28.sp
                    )

                    val profileName = remember { mutableStateOf("") }
                    val newPassword = remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = profileName.value,
                        onValueChange = { profileName.value = it },
                        label = { stringResource(R.string.profile_name) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Filled.Person, null) },
                        trailingIcon = { Icon(Icons.Filled.Clear, null) }
                    )

                    OutlinedTextField(
                        value = newPassword.value,
                        onValueChange = { newPassword.value = it },
                        label = { stringResource(R.string.new_password) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Filled.Lock, null) },
                        trailingIcon = { Icon(Icons.Filled.Clear, null) }
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route) {
                        navController.backQueue.clear()
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("editButton")
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun editProfileConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val accountCard = createRefFor("accountCard")
        val editCard = createRefFor("editCard")
        val editButton = createRefFor("editButton")

        constrain(accountCard) {
            top.linkTo(parent.top, 30.dp)
        }

        constrain(editCard) {
            top.linkTo(accountCard.bottom, 50.dp)
        }

        constrain(editButton) {
            bottom.linkTo(parent.bottom, 20.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}