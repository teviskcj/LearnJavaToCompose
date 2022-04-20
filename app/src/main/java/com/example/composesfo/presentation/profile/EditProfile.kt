package com.example.composesfo.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.common.CurrentUserState
import com.example.composesfo.data.remote.dto.QuestionDto
import com.example.composesfo.data.remote.dto.UserDto
import com.example.composesfo.domain.model.Question
import com.example.composesfo.presentation.component.ExposedDropMenuStateHolder
import com.example.composesfo.presentation.component.TextFieldWithIcon
import com.example.composesfo.presentation.component.TopBarTitle
import com.example.composesfo.presentation.component.rememberExposedMenuStateHolder
import com.example.composesfo.presentation.ui.theme.AllButton
import com.example.composesfo.presentation.ui.theme.white

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val userProfile = state.userProfile
    val stateQuestion = viewModel.stateQuestion.value
    val question = stateQuestion.question
    val stateHolder = rememberExposedMenuStateHolder()
    val stateHolderTwo = rememberExposedMenuStateHolder()

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
            onShowQuestionFieldChange = viewModel::onShowQuestionFieldChange,
            stateHolder = stateHolder,
            stateHolderTwo = stateHolderTwo,
            createQuestion = viewModel::createQuestion
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
                question?.let {
                    QuestionsCard(
                        onShowQuestionFieldChange = viewModel::onShowQuestionFieldChange,
                        questionOne = stateHolder.value,
                        questionTwo = stateHolderTwo.value,
                        question = it
                    )
                }
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
    onShowQuestionFieldChange: (Boolean) -> Unit,
    questionOne: String,
    questionTwo: String,
    question: Question
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
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = questionOne,
                fontSize = 20.sp
            )

            Text(
                text = question.answer1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = questionTwo,
                fontSize = 20.sp
            )

            Text(
                text = question.answer2,
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
    onShowQuestionFieldChange: (Boolean) -> Unit,
    stateHolder: ExposedDropMenuStateHolder,
    stateHolderTwo: ExposedDropMenuStateHolder,
    createQuestion: (String, QuestionDto) -> Unit
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

            Text(
                text = "You can set up to two questions",
                fontSize = 20.sp,
            )

            ExposedDropdownMenu(stateHolder = stateHolder)

            TextFieldWithIcon(
                text = answerOne,
                onTextChange = onAnswerOneChange,
                label = "Answer 1"
            )

            ExposedDropdownMenu(stateHolder = stateHolderTwo)

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
                    val questionDto = QuestionDto(
                        answer1 = answerOne,
                        answer2 = answerTwo
                    )

                    createQuestion(CurrentUserState.userId, questionDto)
                    onShowQuestionFieldChange(false)
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

@Composable
fun ExposedDropdownMenu(stateHolder: ExposedDropMenuStateHolder) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Box (
            modifier = Modifier.fillMaxWidth()
        )  {
            OutlinedTextField(
                value = stateHolder.value,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Question 1") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = stateHolder.icon),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            stateHolder.onEnabledChange(!stateHolder.enabled)
                        }
                    )
                },
                modifier = Modifier
                    .onGloballyPositioned {
                        stateHolder.onSizeChange(it.size.toSize())
                    }
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabledChange(false)
                },
                modifier = Modifier.width(with(LocalDensity.current){stateHolder.size.width.toDp()})
            ) {
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            stateHolder.onSelectedIndexChange(index)
                            stateHolder.onEnabledChange(false)
                        }
                    ) {
                        Text(text = s)
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}