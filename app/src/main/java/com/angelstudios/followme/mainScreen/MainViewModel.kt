package com.angelstudios.followme.mainScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelstudios.framework.di.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {


//    @OptIn(SavedStateHandleSaveableApi::class)
//     var loginUiState by savedStateHandle.saveable {
//        mutableStateOf(LoginScreenUiState())
//    }
//    private set


    private val _showSnackBar = Channel<String>()
    val showSnackBar = _showSnackBar.receiveAsFlow()

    private val _showLocationsSettingsDialog = Channel<Boolean>()
    val showLocationsSettingsDialog = _showLocationsSettingsDialog.receiveAsFlow()

    private val _startLocationServices = Channel<Boolean>()
    val startLocationServices = _startLocationServices.receiveAsFlow()







//    fun onEvent(event: LoginScreenEvent) {
//        when (event) {
//            is LoginScreenEvent.EmailChanged -> {
//                loginUiState = loginUiState.copy(email = event.email)
//            }
//            is LoginScreenEvent.PasswordChanged -> {
//                loginUiState = loginUiState.copy(password = event.password)
//            }
//            LoginScreenEvent.GoToForgetPasswordScreen -> {
//                navigateTo(NavigationEvent.ToForgetPasswordScreen)
//            }
//            LoginScreenEvent.GoToSignInScreen -> {
//                navigateTo(NavigationEvent.ToSignInScreen)
//            }
//            LoginScreenEvent.Skip -> {
//                navigateTo(NavigationEvent.Skip)
//            }
//            LoginScreenEvent.Submit -> {
//                login()
//            }
//        }
//    }


    fun startStopUpdatingLocation() {
        viewModelScope.launch(dispatchers.mainImmediate) {
            _startLocationServices.send(true)
        }
    }

    fun showGoToSettingsDialog() {
        viewModelScope.launch(dispatchers.mainImmediate) {
            _showLocationsSettingsDialog.send(true)
        }
    }

    fun hideGoToSettingsDialog() {
        viewModelScope.launch(dispatchers.mainImmediate) {
            _showLocationsSettingsDialog.send(false)
        }
    }

    fun showSnackBar(msg: String) {
        viewModelScope.launch(dispatchers.mainImmediate) {
            _showSnackBar.send(msg)
        }
    }

}
