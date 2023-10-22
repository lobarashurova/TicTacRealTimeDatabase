package uz.mlsoft.tictacrealtimedatabase.presentation.user_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mlsoft.tictacrealtimedatabase.ui.components.UserItem
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber

class UsersScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: UsersContract.ViewModel = getViewModel<UsersViewModel>()
        TicTacRealTimeDatabaseTheme {
            val context = LocalContext.current
            UsersScreenContent(uiState = viewModel.collectAsState().value, viewModel::onDispatcher)

            viewModel.collectSideEffect {
                when (it) {
                    is UsersContract.SideEffect.ToastData -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

//        LaunchedEffect(lifecycleState) {
//            when (lifecycleState) {
//                Lifecycle.State.DESTROYED -> {
//                    myTimber("on destroy")
//                    viewModel.onDispatcher(UsersContract.Intent.DeletePlayer)
//                }
//
//                Lifecycle.State.INITIALIZED -> {}
//                Lifecycle.State.CREATED -> {}
//                Lifecycle.State.STARTED -> {}
//                Lifecycle.State.RESUMED -> {}
//            }
//        }
    }

}


@Composable
fun UsersScreenContent(
    uiState: UsersContract.UiState,
    onEventDispatcher: (UsersContract.Intent) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Player's list",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(top = 15.dp)
                .align(Alignment.CenterHorizontally)
        )

        myTimber("size screen:${uiState.list}")
        LazyColumn {
            items(uiState.list) {
                UserItem(data = it) {
                    myTimber("data: $it")
                    onEventDispatcher.invoke(
                        UsersContract.Intent.ClickPlayerItem(
                            it.userId,
                            it.userName
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UserScreenPrev() {
    TicTacRealTimeDatabaseTheme {
        UsersScreenContent(UsersContract.UiState()) {

        }
    }
}

