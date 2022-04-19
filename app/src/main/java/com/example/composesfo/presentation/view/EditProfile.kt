package com.example.composesfo.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.presentation.component.TextFieldWithIcon
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.foodDetails.FoodDetailsViewModel
import com.example.composesfo.presentation.navigation.Screen
import com.example.composesfo.presentation.profile.ProfileViewModel
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val userProfile = state.userProfile

    if (viewModel.showNameField) {
        userProfile?.let {
            EditNameField(
                name = viewModel.name,
                onNameChange = viewModel::onNameChange,
                onShowNameFieldChange = viewModel::onShowNameFieldChange,
                password = it.password,
                createUser = viewModel::createUser
            )
        }
    }
    if (viewModel.showPasswordField) {
        userProfile?.let {
            EditPasswordField(
                password = viewModel.password,
                onPasswordChange = viewModel::onPasswordChange,
                confirmPassword = viewModel.confirmPassword,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                onShowPasswordFieldChange = viewModel::onShowPasswordFieldChange,
                name = it.name,
                createUser = viewModel::createUser
            )
        }
    }
    if (viewModel.showQuestionField) {
        SetQuestionField(
            answerOne = viewModel.answerOne,
            answerTwo = viewModel.answerTwo,
            onAnswerOneChange = viewModel::onAnswerOneChange,
            onAnswerTwoChange = viewModel::onAnswerTwoChange,
            onShowQuestionFieldChange = viewModel::onShowNameFieldChange
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!viewModel.showNameField && !viewModel.showPasswordField && !viewModel.showQuestionField) {
                TopBarTitle(
                    textOne = "Account",
                    textTwo = "Information"
                )
                Spacer(modifier = Modifier.padding(10.dp))
                userProfile?.let {
                    viewModel.onNameChange(it.name)
                    NameCard(
                        name = it.name,
                        onShowNameFieldChange = viewModel::onShowNameFieldChange
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                userProfile?.let {
                    PasswordCard(
                        password = it.password,
                        onShowPasswordFieldChange = viewModel::onShowPasswordFieldChange
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))
                QuestionsCard(
                    onShowQuestionFieldChange = viewModel::onShowQuestionFieldChange
                )
            }
        }
    }
}

@Composable
fun NameCard(
    name: String,
    onShowNameFieldChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowNameFieldChange(true) }
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
                    text = "Name",
                    fontSize = 20.sp,
                )


                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit Name",
                    tint = AllButton
                )

            }


            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PasswordCard(
    password: String,
    onShowPasswordFieldChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onShowPasswordFieldChange(true) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Password",
                    fontSize = 20.sp,
                )


                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit Password",
                    tint = AllButton
                )

            }


            Text(
                text = password,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QuestionsCard(
    onShowQuestionFieldChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onShowQuestionFieldChange(true) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Security Questions",
                    fontSize = 20.sp,
                )


                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Set Security Questions",
                    tint = AllButton
                )

            }


            Text(
                text = "Question 1",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Question 2",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EditNameField(
    name: String,
    onNameChange: (String) -> Unit,
    onShowNameFieldChange: (Boolean) -> Unit,
    password: String,
    createUser: (String, UserDto) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "This is how we'll address you",
                fontSize = 20.sp,
            )

            TextFieldWithIcon(
                text = name,
                onTextChange = onNameChange,
                label = stringResource(R.string.name),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                          onShowNameFieldChange(false)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = AllButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 30.dp,
                        bottom = 20.dp
                    )
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    val userDto = UserDto(
                        name = name,
                        password = password,
                        phone = CurrentUserState.userId
                    )

                    createUser(CurrentUserState.userId, userDto)
                    onShowNameFieldChange(false)
                },
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
                    text = "Save",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun EditPasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onShowPasswordFieldChange: (Boolean) -> Unit,
    name: String,
    createUser: (String, UserDto) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "This is how we'll help you to reset password",
                fontSize = 20.sp,
            )

            TextFieldWithIcon(
                text = password,
                onTextChange = onPasswordChange,
                label = "New Password"
            )

            TextFieldWithIcon(
                text = confirmPassword,
                onTextChange = onConfirmPasswordChange,
                label = "Confirm Password",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onShowPasswordFieldChange(false)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = AllButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 30.dp,
                        bottom = 20.dp
                    )
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    val userDto = UserDto(
                        name = name,
                        password = password,
                        phone = CurrentUserState.userId
                    )

                    createUser(CurrentUserState.userId, userDto)
                    onShowPasswordFieldChange(false)
                },
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
                    text = "Reset",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SetQuestionField(
    answerOne: String,
    answerTwo: String,
    onAnswerOneChange: (String) -> Unit,
    onAnswerTwoChange: (String) -> Unit,
    onShowQuestionFieldChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "This is how we'll enhance your account security",
                fontSize = 20.sp,
            )

            TextFieldWithIcon(
                text = answerOne,
                onTextChange = onAnswerOneChange,
                label = "Answer 1"
            )

            TextFieldWithIcon(
                text = answerTwo,
                onTextChange = onAnswerTwoChange,
                label = "Answer 2",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onShowQuestionFieldChange(false)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = AllButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 30.dp,
                        bottom = 20.dp
                    )
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                },
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
                    text = "Set Questions",
                    color = white,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}