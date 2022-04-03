package com.example.composesfo.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LanguageScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            constraintSet = languageScreenConstraintSet()
        ) {
            Text(
                text = "Select Application Language",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.layoutId("languageText")
            )

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("englishButton")
            ) {
                Text(
                    text = "ENGLISH",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("chineseButton")
            ) {
                Text(
                    text = "CHINESE",
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun languageScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val languageText = createRefFor("languageText")
        val englishButton = createRefFor("englishButton")
        val chineseButton = createRefFor("chineseButton")

        constrain(languageText) {
            top.linkTo(parent.top, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(englishButton) {
            top.linkTo(languageText.bottom, 100.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(chineseButton) {
            top.linkTo(englishButton.bottom, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(navController = rememberNavController())
}