package uz.mlsoft.tictacrealtimedatabase.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.mlsoft.tictacrealtimedatabase.domain.GameRepository
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val direction: RegistrationDirection,
    private val repository: GameRepository
) : RegistrationContract.ViewModel, ViewModel() {
    override fun onDispatcher(intent: RegistrationContract.Intent) = intent {
        when (intent) {
//            RegistrationContract.Intent.clickRegisterButton -> {
//                direction.moveToUserScreen()
//            }

            is RegistrationContract.Intent.UserRegister -> {
                myTimber("user registering")
                myTimber("name:${intent.name}")
                repository.addUsers(intent.name)
                    .onEach {
                        it.onSuccess {
                            direction.moveToUserScreen()
                            myTimber("succes added")
                            postSideEffect(RegistrationContract.SideEffect.ToastData("Successfully submitted!"))
                        }
                        it.onFailure {
                            myTimber("added failure")
                            postSideEffect(
                                RegistrationContract.SideEffect.ToastData(
                                    it.message ?: "Connnection error"
                                )
                            )
                        }
                    }.launchIn(viewModelScope)
            }
        }


    }

    override val container =
        container<RegistrationContract.UiState, RegistrationContract.SideEffect>(
            RegistrationContract.UiState.InitState
        )
}