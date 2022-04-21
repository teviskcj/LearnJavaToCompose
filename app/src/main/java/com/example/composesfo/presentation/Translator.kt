package com.example.composesfo.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.presentation.component.TextFieldWithNoIcon
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.translator.TranslatorViewModel
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun TranslatorScreen(
    navController: NavController,
    viewModel: TranslatorViewModel = hiltViewModel()
) {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TopBarTitle(
                textOne = "Our",
                textTwo = "Translator"
            )

            Spacer(modifier = Modifier.padding(10.dp))

            TranslatorFieldBox(
                input = viewModel.inputTranslator,
                onInputTranslatorChange = viewModel::onInputTranslatorChange,
                result = "(Translate Result)"
            )
            Spacer(modifier = Modifier.weight(1f))
            TranslateButton(
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route) {
                        navController.backQueue.clear()
                    }
                }
            )
        }
    }
}

@Composable
fun TranslatorFieldBox(
    input: String,
    result: String,
    onInputTranslatorChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.english_language),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        TextFieldWithNoIcon(
            text = input,
            onTextChange = onInputTranslatorChange,
            label = stringResource(R.string.please_enter_word),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.to),
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )

        Text(
            text = stringResource(R.string.chinese_language),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        Text(
            text = result,
            textAlign = TextAlign.Center,
            fontSize = 28.sp
        )
    }

}

@Composable
fun TranslateButton(
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = onClick,
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
                text = stringResource(R.string.translate),
                color = white,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                contentDescription = stringResource(R.string.voice_button),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranslatorScreenPreview() {
    TranslatorScreen(navController = rememberNavController())
}