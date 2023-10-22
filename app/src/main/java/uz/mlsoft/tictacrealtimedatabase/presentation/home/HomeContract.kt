package uz.mlsoft.tictacrealtimedatabase.presentation.home

import org.orbitmvi.orbit.ContainerHost
import uz.mlsoft.tictacrealtimedatabase.data.common.GameCommonData

interface HomeContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatcher(intent: Intent)
    }

    interface SideEffect {
        data class ToastData(val message: String) : SideEffect
    }

    data class UIState(val data: GameCommonData? = null)
    interface Intent {
        object ClickBack :Intent
        data class Move(val data: GameCommonData, val newContainer: String) : Intent
    }
}