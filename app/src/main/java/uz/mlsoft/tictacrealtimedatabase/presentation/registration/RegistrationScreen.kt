package uz.mlsoft.tictacrealtimedatabase.presentation.registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mlsoft.tictacrealtimedatabase.R
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber

class RegistrationScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: RegistrationContract.ViewModel = getViewModel<RegistrationViewModel>()
        val context = LocalContext.current
        viewModel.collectSideEffect {
            when (it) {
                is RegistrationContract.SideEffect.ToastData -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        RegistrationScreenContent(
            uiState = viewModel.collectAsState().value,
            onEventDispatcher = viewModel::onDispatcher
        )
    }

}


@Composable
fun RegistrationScreenContent(
    uiState: RegistrationContract.UiState,
    onEventDispatcher: (RegistrationContract.Intent) -> Unit
) {
    var userName by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_start))
            val progress by animateLottieCompositionAsState(composition)
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            OutlinedTextField(
                value = userName, onValueChange = { userName = it },
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            )


            Button(
                onClick = {
                    myTimber("clicked")
                    onEventDispatcher.invoke(RegistrationContract.Intent.UserRegister(userName))
                }, modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Add User", fontSize = 22.sp)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun RegistrationPrev() {
    TicTacRealTimeDatabaseTheme {
        RegistrationScreenContent(uiState = RegistrationContract.UiState.InitState) {}
    }
}

