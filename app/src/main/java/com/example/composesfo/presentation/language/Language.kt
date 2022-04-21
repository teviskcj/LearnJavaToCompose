package com.example.composesfo.presentation.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.common.StoreUserLanguage
import com.example.composesfo.presentation.ui.theme.AllButton
import com.localazy.android.Localazy
import com.localazy.android.LocalazyLocale

@Composable
fun LanguageScreen(
    navController: NavController,
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStore = StoreUserLanguage(context)
    val userLanguage = dataStore.getLanguage.collectAsState(initial = "")

    if (viewModel.openDialog) {
        LocaleSwitcher(
            items = viewModel.locales,
            onChange = {
                viewModel.saveLanguage(dataStore,it.localizedName)

                // Change the locale and persist the new choice.
                Localazy.forceLocale(it.locale, true)
                viewModel.onOpenDialogChange(false)
                // Reopen MainActivity with clearing top.
                /*startActivity(
                    Intent(
                        this@SwitchActivity,
                        MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                )*/

            },
            onHelp = {
                // Open the project on Localazy to allow contributors to help us with translating.
                /*startActivity(
                    Intent(Intent.ACTION_VIEW, Localazy.getProjectUri()).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )*/
            },
            onDismiss = {
                viewModel.onOpenDialogChange(false)
            }
        )
    }

    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!viewModel.openDialog) {
                LanguageCard(
                    language = userLanguage.value!!,
                    onShowDialogChange = viewModel::onOpenDialogChange
                )
            }

        }
    }


}

@Composable
fun LanguageCard(
    language: String,
    onShowDialogChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowDialogChange(true) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.language),
                    fontSize = 20.sp,
                )


                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.change_language),
                    tint = AllButton
                )

            }

            Text(
                text = language,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LocaleSwitcher(
    items: List<LocalazyLocale>,
    onChange: (LocalazyLocale) -> Unit,
    onHelp: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.subtitle1
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    LazyColumn(modifier = Modifier.padding(0.dp, 8.dp)) {
                        items(items) {
                            TextButton(
                                onClick = {
                                    onChange(it)
                                },
                                modifier = Modifier
                                    .padding(16.dp, 4.dp, 4.dp, 4.dp)
                                    .fillMaxWidth()
                            ) {
                                val name =
                                    "${it.localizedName}${if (!it.isFullyTranslated) " (incomplete)" else ""}"
                                Text(name)
                            }
                        }

                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(navController = rememberNavController())
}