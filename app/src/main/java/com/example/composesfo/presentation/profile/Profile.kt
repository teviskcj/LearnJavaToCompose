package com.example.composesfo.presentation.profile

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

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Box(modifier = Modifier
        .fillMaxSize()) {
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
                    painter = painterResource(id = R.drawable.ic_profile),
                    text = stringResource(R.string.profile),
                    onCardClick = {
                        navController.navigate(route = Screen.EditProfileScreen.route)
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
                    painter = painterResource(id = R.drawable.ic_language),
                    text = stringResource(R.string.language),
                    onCardClick = {
                        navController.navigate(route = Screen.LanguageScreen.route)
                    }
                )

                ActionCard(
                    painter = painterResource(id = R.drawable.ic_translate),
                    text = stringResource(R.string.translator),
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
                tint = AllButton,
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
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}