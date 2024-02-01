package ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.components.SimpleLoading
import ui.components.SlideMessage
import ui.screens.auth.AuthViewModel
import ui.screens.auth.LogInUiState
import ui.screens.auth.authViewModelFactory
import ui.screens.auth.authViewModelKey
import ui.screens.auth.signup.SignUpScreen
import ui.screens.home.HomeScreen

data class LogInScreen(
    private val authViewModel: AuthViewModel,
    val checkUserLogIn: () -> Unit
) : Screen {

    @Composable
    override fun Content() {

//        val authViewModel = getViewModel(authViewModelKey, authViewModelFactory)
//        val authViewModel = viewModel<AuthViewModel>()

        val navigator = LocalNavigator.currentOrThrow

        LogInScreenContent(
            uiState = authViewModel.logInUiState,
            onLogInClick = { mail, pass ->
                authViewModel.logIn(mail, pass)
            },
            onSignUpClick = {
                navigator.push(SignUpScreen(authViewModel))
            },
            resetResult = {
                authViewModel.resetResult()
            },
            navigateToHome = {
                checkUserLogIn()
            },
            viewModel = authViewModel

        )
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
fun LogInScreenContent(
    uiState: LogInUiState,
    onLogInClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    resetResult: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: AuthViewModel
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    if (uiState.token.isNotEmpty()) {
        navigateToHome()
    }

    if (uiState.loading) {
        keyboardController?.hide()
        SimpleLoading()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
        ) {

            SlideMessage(uiState.result) {
                resetResult()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painterResource("appicon.png"),
                    modifier = Modifier
                        .size(75.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(18.dp))

                TextField(
                    value = viewModel.logInUserMail,
                    onValueChange = { mail -> viewModel.updateLogInUserMail(mail) },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = viewModel.logInUserPassword,
                    onValueChange = { password -> viewModel.updateLogInUserPass(password) },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onLogInClick(viewModel.logInUserMail, viewModel.logInUserPassword)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = !uiState.loading
                ) {
                    Text("Log In")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account")
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(
                        onClick = onSignUpClick
                    ) {
                        Text("Sign Up")
                    }
                }
            }

        }
    }
}
