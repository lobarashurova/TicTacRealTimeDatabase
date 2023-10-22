package uz.mlsoft.tictacrealtimedatabase.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mlsoft.tictacrealtimedatabase.R
import uz.mlsoft.tictacrealtimedatabase.data.common.UserCommonData
import uz.mlsoft.tictacrealtimedatabase.ui.theme.TicTacRealTimeDatabaseTheme

@Composable
fun UserItem(
    data: UserCommonData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }, shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = " name:${data.userName}", fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 25.dp), color = Color.Black
            )

            Image(
                painter = painterResource(R.drawable.star),
                contentDescription = "star",
                modifier = Modifier
                    .padding(end = 15.dp)
                    .align(Alignment.CenterEnd)
                    .size(50.dp)
            )

        }


    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPrev() {
    TicTacRealTimeDatabaseTheme() {
        UserItem(data = UserCommonData("", "Princessa")){}
    }
}