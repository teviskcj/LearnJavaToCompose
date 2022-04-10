package com.example.composesfo.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
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
fun QuestionsScreen(
    navController: NavController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            constraintSet = questionsScreenConstraintSet()
        ) {
            Text(
                text = "Please set Answer for the Following Security Questions?",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                modifier = Modifier.layoutId("questionText")
            )

            val answerOne = remember { mutableStateOf("") }
            val answerTwo = remember { mutableStateOf("") }
            OutlinedTextField(
                value = answerOne.value,
                onValueChange = { answerOne.value = it },
                placeholder = { stringResource(R.string.question_one) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputQuestionOne")
            )

            OutlinedTextField(
                value = answerTwo.value,
                onValueChange = { answerTwo.value = it },
                placeholder = { stringResource(R.string.question_two) },
                singleLine = true,
                trailingIcon = { Icon(Icons.Filled.Clear, null) },
                modifier = Modifier.layoutId("inputQuestionTwo")
            )

            Button(
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route) {
                        navController.backQueue.clear()
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .layoutId("setButton")
            ) {
                Text(
                    text = "SET",
                    fontSize = 22.sp
                )
            }
        }
    }
}

private fun questionsScreenConstraintSet(): ConstraintSet {
    return ConstraintSet{
        val questionText = createRefFor("questionText")
        val inputQuestionOne = createRefFor("inputQuestionOne")
        val inputQuestionTwo = createRefFor("inputQuestionTwo")
        val setButton = createRefFor("setButton")

        constrain(questionText) {
            top.linkTo(parent.top, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(inputQuestionOne) {
            top.linkTo(questionText.bottom, 50.dp)
            centerHorizontallyTo(parent)
        }

        constrain(inputQuestionTwo) {
            top.linkTo(inputQuestionOne.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(setButton) {
            bottom.linkTo(parent.bottom, 30.dp)
            centerHorizontallyTo(parent)
            width = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionsScreenPreview() {
    QuestionsScreen(navController = rememberNavController())
}