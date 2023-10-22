package uz.mlsoft.tictacrealtimedatabase.presentation.user_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.mlsoft.tictacrealtimedatabase.domain.GameRepository
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: GameRepository,
    private val direction: UsersDirection

) : ViewModel(), UsersContract.ViewModel {



    override val container =
        container<UsersContract.UiState, UsersContract.SideEffect>(UsersContract.UiState(emptyList()))

    init {
        repository.usersListFlow.onEach {
            myTimber("list size viewModel: ${it.size} ")
            intent {
                reduce { UsersContract.UiState(it) }
            }
        }.launchIn(viewModelScope)
        repository.competitionFlow.onEach {
            it?.let {
                intent {
                    val data = it
                    direction.moveToHomeScreen()
                    postSideEffect(UsersContract.SideEffect.ToastData(it.toString()))
                }
            }
        }.launchIn(viewModelScope)

    }

    override fun onDispatcher(intent: UsersContract.Intent) = intent {
        when (intent) {
            is UsersContract.Intent.ClickPlayerItem -> {
                repository.connectingUsers(intent.id, intent.name)
                    .onEach {
                        it.onSuccess {
                            direction.moveToHomeScreen()
                        }
                        it.onFailure {
                            postSideEffect(
                                UsersContract.SideEffect.ToastData(
                                    it.message ?: "Error"
                                )
                            )
                        }
                    }.launchIn(viewModelScope)
            }

            UsersContract.Intent.DeletePlayer -> {
                repository.deleteMySelf()
            }
        }
    }

}