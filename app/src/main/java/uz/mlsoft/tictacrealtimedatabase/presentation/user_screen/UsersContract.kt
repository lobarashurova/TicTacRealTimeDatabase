package uz.mlsoft.tictacrealtimedatabase.presentation.user_screen

import org.orbitmvi.orbit.ContainerHost
import uz.mlsoft.tictacrealtimedatabase.data.common.UserCommonData

sealed class UsersContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onDispatcher(intent: Intent)
    }

    data class UiState(
        val list: List<UserCommonData> = listOf()
    )

    interface SideEffect {
        data class ToastData(val message: String) : SideEffect
    }

    interface Intent {
        data class ClickPlayerItem(val id: String, val name: String):Intent
        object DeletePlayer:Intent
    }
}
