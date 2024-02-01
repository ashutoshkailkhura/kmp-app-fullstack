package ui.screens.auth

import dev.icerock.moko.mvvm.compose.ViewModelFactory
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel

val authViewModelFactory = viewModelFactory {
    AuthViewModel()
}

const val authViewModelKey = 440

//val authViewModel = getViewModel(authViewModelKey, authViewModelFactory)