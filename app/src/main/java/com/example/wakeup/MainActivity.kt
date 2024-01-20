package com.example.wakeup



import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

          MyTheme {
                MainScreen()
            }
        }

    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
                contentAlignment = Alignment.Center

    ){
    Image(

        painter = painterResource(id = R.drawable.img3),

        contentDescription = null,
        modifier = Modifier
            .size(500.dp)
            .padding(bottom = 50.dp),


        contentScale = ContentScale.Crop,


    )

    Text(text = "FriendlyAlarm by Lorenzo Guerrini",
        modifier=Modifier
            .align(Alignment.BottomCenter),
           // .padding(bottom = 100.dp),
    fontSize=10.sp,
        color=Color.Black)
   }
    if(isAggressive){
        Image(

            painter = painterResource(id = R.drawable.img2),

            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
TimePickerWithDialog()
}
    if(isAggressive==true && isQuiz==true){ QuizContent()}
}







@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MyTheme {
        MainScreen()
       // QuizContent()
    }
}