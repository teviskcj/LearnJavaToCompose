package com.example.composesfo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.navigation.Screen
import com.example.composesfo.ui.theme.AllButton

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(modifier = Modifier.fillMaxSize(),
            constraintSet = profileScreenConstraintSet()
        ) {
            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("profileBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f)
                    .clickable (
                        onClick = { navController.navigate(route = Screen.EditProfileScreen.route) }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.AccountBox,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Profile",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("orderBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.List,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Order",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("languageBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f)
                    .clickable (
                        onClick = { navController.navigate(route = Screen.LanguageScreen.route) }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Language",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("translatorBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Translator",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("questionsBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f)
                    .clickable (
                        onClick = { navController.navigate(route = Screen.QuestionsScreen.route) }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Questions",
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.layoutId("logoutBox")
            ) {
                Box(modifier = Modifier
                    .size(150.dp)
                    .aspectRatio(1f)
                    .clickable (
                        onClick = { navController.popBackStack() }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.ExitToApp,
                            contentDescription = null,
                            tint = AllButton,
                            modifier = Modifier.size(48.dp)
                        )

                        Text(
                            text = "Logout",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

private fun profileScreenConstraintSet() : ConstraintSet {
    return ConstraintSet {
        val profileBox = createRefFor("profileBox")
        val orderBox = createRefFor("orderBox")
        val languageBox = createRefFor("languageBox")
        val translatorBox = createRefFor("translatorBox")
        val questionsBox = createRefFor("questionsBox")
        val logoutBox = createRefFor("logoutBox")

        constrain(profileBox) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(orderBox) {
            start.linkTo(profileBox.end)
            top.linkTo(profileBox.top)
            bottom.linkTo(profileBox.bottom)
        }

        createHorizontalChain(
            profileBox, orderBox,
            chainStyle = ChainStyle.Spread
        )

        constrain(languageBox) {
            start.linkTo(profileBox.start)
            end.linkTo(profileBox.end)
            top.linkTo(profileBox.bottom)
        }

        constrain(translatorBox) {
            top.linkTo(languageBox.top)
            bottom.linkTo(languageBox.bottom)
            start.linkTo(orderBox.start)
            end.linkTo(orderBox.end)
        }

        createHorizontalChain(
            languageBox, translatorBox,
            chainStyle = ChainStyle.Spread
        )

        constrain(questionsBox) {
            top.linkTo(languageBox.bottom)
            start.linkTo(languageBox.start)
            end.linkTo(languageBox.end)
        }

        constrain(logoutBox) {
            top.linkTo(questionsBox.top)
            bottom.linkTo(questionsBox.bottom)
            start.linkTo(translatorBox.start)
            end.linkTo(translatorBox.end)
        }

        createHorizontalChain(
            questionsBox, logoutBox,
            chainStyle = ChainStyle.Spread
        )
        
        createVerticalChain(
            profileBox, languageBox, questionsBox,
            chainStyle = ChainStyle.Spread
        )

        createVerticalChain(
            orderBox, translatorBox, logoutBox,
            chainStyle = ChainStyle.Spread
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}