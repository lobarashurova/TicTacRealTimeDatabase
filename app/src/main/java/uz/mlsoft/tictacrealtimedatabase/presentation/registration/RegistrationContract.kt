package uz.mlsoft.tictacrealtimedatabase.presentation.registration

import org.orbitmvi.orbit.ContainerHost

sealed interface RegistrationContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onDispatcher(intent: Intent)
    }

    interface UiState {
        object InitState : UiState
    }

    interface SideEffect {
        data class ToastData(val message: String) : SideEffect
    }

    interface Intent {
        data class UserRegister(val name: String) : Intent
        object clickRegisterButton : Intent
    }
}