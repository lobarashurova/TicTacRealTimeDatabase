package uz.mlsoft.tictacrealtimedatabase.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme

@Composable
fun GameDialog(
    onDismissRequest: () -> Unit,
    onClickYes: () -> Unit, name: String
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "$name is inviting you to game! \n Do you accept this invitation?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { onClickYes() },
                        modifier = Modifier
                            .padding(top = 25.dp, start = 15.dp)
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                    ) {
                        Text(text = " Yes", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .padding(top = 25.dp, end = 15.dp)
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
                    ) {
                        Text(text = " No", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameDialogPrev() {
    TicTacRealTimeDatabaseTheme() {
        GameDialog(onDismissRequest = {}, {}, name = "princessa")
    }
}