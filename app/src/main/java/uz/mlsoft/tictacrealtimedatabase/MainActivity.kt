package uz.mlsoft.tictacrealtimedatabase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mlsoft.tictacrealtimedatabase.domain.GameRepository
import uz.mlsoft.tictacrealtimedatabase.navigation.NavigationHandler
import uz.mlsoft.tictacrealtimedatabase.presentation.registration.RegistrationScreen
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appNavigationHandler: NavigationHandler
    @Inject lateinit var repository: GameRepository

    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacRealTimeDatabaseTheme {
                Navigator(screen = RegistrationScreen()) { navigate ->
                    appNavigationHandler.uiNavigator
                        .onEach { it.invoke(navigate) }
                        .launchIn(lifecycleScope)
                    CurrentScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        myTimber("on destroy")
        repository.deleteMySelf()
        super.onDestroy()
    }

    override fun onPause() {
        myTimber("on pause")
        repository.deleteMySelf()
        super.onPause()
    }

}