package com.example.composesfo.presentation.admin.adminHome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.admin_dominant
import com.example.composesfo.presentation.ui.theme.lightgraybg
import com.example.composesfo.presentation.ui.theme.orange

@Composable
fun AdminHomeScreen(
    navController: NavController
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(lightgraybg)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionCard(
                    painter = painterResource(id = R.drawable.ic_menu_book),
                    text = stringResource(R.string.menu),
                    onCardClick = {
                        navController.navigate(route = Screen.AdminFoodMenuScreen.route)
                    }
                )

                ActionCard(
                    painter = painterResource(id = R.drawable.ic_order),
                    text = stringResource(R.string.order),
                    onCardClick = {
                        navController.navigate(route = Screen.OrderScreen.route)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionCard(
                    painter = painterResource(id = R.drawable.ic_history),
                    text = stringResource(R.string.history),
                    onCardClick = {
                        navController.navigate(route = Screen.LanguageScreen.route)
                    }
                )

                ActionCard(
                    painter = painterResource(id = R.drawable.ic_driver),
                    text = stringResource(R.string.driver),
                    onCardClick = {
                        navController.navigate(route = Screen.TranslatorScreen.route)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionCard(
                    painter = painterResource(id = R.drawable.ic_logout),
                    text = stringResource(R.string.logout),
                    onCardClick = {
                        navController.navigate(Screen.LoginScreen.route) {
                            navController.backQueue.clear()
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun ActionCard(
    painter: Painter,
    text: String,
    onCardClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .clickable { onCardClick() }
            .size(140.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = orange,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = text,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminHomeScreenPreview() {
    AdminHomeScreen(navController = rememberNavController())
}