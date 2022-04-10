package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.composesfo.R
import com.example.composesfo.presentation.navigation.Screen

@Composable
fun TranslatorScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            constraintSet = translatorScreenConstraintSet()
        ) {
            Text(
                text = "English Language",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.layoutId("englishText")
            )

            val inputEnglishWord = remember { mutableStateOf("") }
            OutlinedTextField(
                value = inputEnglishWord.value,
                onValueChange = { inputEnglishWord.value = it },
                placeholder = { stringResource(R.string.enter_word) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputEnglish")
            )

            Text(
                text = "To",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                modifier = Modifier.layoutId("toText")
            )

            Text(
                text = "Chinese Language",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.layoutId("chineseText")
            )

            val result = remember { mutableStateOf("(Translate Result)") }
            Text(
                text = result.value,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                modifier = Modifier.layoutId("resultText")
            )

            Button(
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route) {
                        navController.backQueue.clear()
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("translateButton")
            ) {
                Text(
                    text = "Translate",
                    fontSize = 22.sp
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .layoutId("voiceButton")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_voice),
                    contentDescription = "Voice Button",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

private fun translatorScreenConstraintSet(): ConstraintSet {
    return ConstraintSet{
        val englishText = createRefFor("englishText")
        val inputEnglish = createRefFor("inputEnglish")
        val toText = createRefFor("toText")
        val chineseText = createRefFor("chineseText")
        val resultText = createRefFor("resultText")
        val translateButton = createRefFor("translateButton")
        val voiceButton = createRefFor("voiceButton")

        constrain(englishText) {
            top.linkTo(parent.top, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(inputEnglish) {
            top.linkTo(englishText.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(toText) {
            top.linkTo(inputEnglish.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(chineseText) {
            top.linkTo(toText.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(resultText) {
            top.linkTo(chineseText.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(translateButton) {
            bottom.linkTo(voiceButton.top, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }

        constrain(voiceButton) {
            bottom.linkTo(parent.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranslatorScreenPreview() {
    TranslatorScreen(navController = rememberNavController())
}