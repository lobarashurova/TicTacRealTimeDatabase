package uz.mlsoft.tictacrealtimedatabase.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme

@Composable
fun WinDialog(
    onDismissRequest: () -> Unit,
    onClickFinish: () -> Unit, name: String
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(215.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "${name.uppercase()} is inviting you to game! \n Do you accept this invitation?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                )


                Button(
                    onClick = {
                        onClickFinish()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .align(CenterHorizontally)
                        .width(150.dp),
                ) {
                    Text(text = "Finish", fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinDialogPrev() {
    TicTacRealTimeDatabaseTheme() {
        WinDialog(onDismissRequest = {}, {}, name = "Princessa")
    }
}