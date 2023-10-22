package uz.mlsoft.tictacrealtimedatabase.presentation.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.mlsoft.tictacrealtimedatabase.R
import uz.mlsoft.tictacrealtimedatabase.data.common.GameCommonData
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme

class HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val context = LocalContext.current
        viewModel.collectSideEffect {
            when (it) {
                is HomeContract.SideEffect.ToastData -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        HomeScreenContent(
            uiState = viewModel.collectAsState().value,
            viewModel::onDispatcher
        )
    }
}

@Composable
fun HomeScreenContent(
    uiState: HomeContract.UIState,
    onDispatcher: (HomeContract.Intent) -> Unit
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_game),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Tic Tac Toe game",
            fontSize = 35.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Red,
            modifier = Modifier
                .padding(top = 25.dp)
                .align(Alignment.TopCenter)
        )

        if (uiState.data == null) {
            CircularProgressIndicator()
        } else {
            Row(
                modifier = Modifier
                    .padding(75.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "player1 \n${uiState.data.firstUserName}",
                    fontWeight = FontWeight.SemiBold, fontSize = 22.sp, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = "player2 \n${uiState.data.secondUserName}",
                    fontWeight = FontWeight.SemiBold, fontSize = 22.sp, textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .size(width * 3 / 5)
                    .align(Alignment.Center), shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, Color.Magenta)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    for (i in 0 until 3) {
                        val charArray = uiState.data.container
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(width / 5)
                        ) {
                            for (j in 0 until 3) {
                                Text(
                                    text = when (charArray[i * 3 + j]) {
                                        '0' -> ""
                                        '1' -> "x"
                                        else -> "o"
                                    },
                                    fontSize = 22.sp,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(width / 5)
                                        .clickable {
                                            val value = if (uiState.data.hasWay) "1" else "2"
                                            onDispatcher.invoke(
                                                HomeContract.Intent.Move(
                                                    uiState.data,
                                                    StringBuilder(uiState.data.container)
                                                        .replace(
                                                            i * 3 + j,
                                                            i * 3 + j + 1,
                                                            value
                                                        )
                                                        .toString()
                                                )
                                            )

                                        }
                                        .border(2.dp, color = Color.Magenta),
                                    textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPrev() {
    TicTacRealTimeDatabaseTheme {
        HomeScreenContent(
            HomeContract.UIState(
                GameCommonData(
                    "",
                    "",
                    "user1",
                    "",
                    "user2",
                    "000000000",
                    true
                )
            )
        ) {}
    }
}