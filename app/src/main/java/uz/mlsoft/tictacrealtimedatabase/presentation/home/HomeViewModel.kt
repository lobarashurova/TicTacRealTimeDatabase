package uz.mlsoft.tictacrealtimedatabase.presentation.home

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
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val directions: HomeDirections,
    private val repository: GameRepository
) : ViewModel(), HomeContract.ViewModel {
    override val container = container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())

    init {
        repository.gamesFlow.onEach {
            it?.let {
                intent {
                    reduce { HomeContract.UIState(it) }
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onDispatcher(intent: HomeContract.Intent) = intent {
        when (intent) {
            is HomeContract.Intent.Move -> {
                repository.updatingContainer(intent.data, intent.newContainer)
                    .onEach {
                        it.onSuccess {

                            postSideEffect(HomeContract.SideEffect.ToastData(""))

                        }
                        it.onFailure {

                            postSideEffect(HomeContract.SideEffect.ToastData("Failed"))

                        }
                    }.launchIn(viewModelScope)
            }

            HomeContract.Intent.ClickBack -> {
                directions.back()
            }
        }
    }

}