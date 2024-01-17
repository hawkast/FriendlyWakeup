package com.example.wakeup

import android.app.TimePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import android.media.MediaPlayer
import kotlin.random.Random
import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import android.os.Handler



import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField

import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.lightColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
//import androidx.compose.material.Text


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*




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


  // Chiamata alla funzione dalla nuova istanza

var isButtonEnabled by  mutableStateOf(true)
var isAlarmRinging by mutableStateOf(false)
var isAggressive by mutableStateOf(false)
var isQuiz by mutableStateOf(false)


@Composable
fun MainScreen() {


   // var isAggressive by remember { mutableStateOf(false) }
    // tracciare il sta suonando

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()

    )
    Image(

        painter = painterResource(id = R.drawable.imgsfondo),

        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
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

        /*
        // Impostazione dell'orario della sveglia
        OutlinedTextField(
            value = alarmTime,
            onValueChange = {
                alarmTime = it
            },
            label = { Text("Orario della Sveglia") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )*/
TimePickerWithDialog()
        // Impostazione dell'opzione aggressiva
/*
        Switch(
            checked = isAggressive,

            onCheckedChange = {
                isAggressive = it

                isButtonEnabled=false

            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Pulsante per impostare l'allarme


        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),

            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,

            ),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 8.dp)
        ) {
            Text("Cancella sveglia")
        }

        // Pulsante per spegnere l'allarme
        Button(
            onClick = {


                isAlarmRinging = false
                stopAlarm()


            },
            enabled= isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            ),
        ) {
            Text("Spegni Sveglia")
        }

    }
    isButtonEnabled = !isAggressive

 */
}
    if(isAggressive==true && isQuiz==true){ QuizContent()}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test() {
}
var mediaPlayer: MediaPlayer? = null

fun setAlarm(alarmTime: String, isAggressive: Boolean, context: Context) {
    val hour = alarmTime.split(":")[0].toInt()
    val minute = alarmTime.split(":")[1].toInt()
  mediaPlayer = MediaPlayer.create(context, R.raw.alarm  )

    val now = Calendar.getInstance()
    val alarmTimeCalendar = now.clone() as Calendar
    alarmTimeCalendar.set(Calendar.HOUR_OF_DAY, hour)
    alarmTimeCalendar.set(Calendar.MINUTE, minute)
    alarmTimeCalendar.set(Calendar.SECOND, 0)
    alarmTimeCalendar.set(Calendar.MILLISECOND, 0)

    val timeDifferenceMillis = alarmTimeCalendar.timeInMillis - now.timeInMillis

    if (timeDifferenceMillis > 0) {
        // Imposta l'allarme
        val handler = Handler()
        handler.postDelayed({
            isAlarmRinging = true
            mediaPlayer?.setOnCompletionListener {
                it.start()
            }
mediaPlayer?.start()
            // Esegui l'azione quando l'allarme scatta
            if (!isAggressive&& isAlarmRinging) {

                showRingMessage(context)
            } else  {
isQuiz=true

                showAggressiveQuestion(context)
            }
        }, timeDifferenceMillis)
    } else {
        // L'orario della sveglia è nel passato, gestisci di conseguenza
        showErrorMessage(context)
    }
}

fun stopAlarm() {
    // Controlla se il MediaPlayer è inizializzato e sta suonando
    mediaPlayer?.let {
        if (it.isPlaying) {
            it.stop()
            it.release()
            mediaPlayer = null
        }
    }
}
fun showRingMessage(context: Context) {


                    Toast.makeText(context, "Sta suonando!", Toast.LENGTH_SHORT).show()

    }
var userAnswer by   mutableStateOf("")
@Composable
fun QuizContent() {
    // Valori prefissati di 'a' e 'b'
    val aValue = 3.0
    val bValue = 5.0
    //val numeroCasuale = generateRandomOrder()
    // Risultato corretto
    val correctResult by remember { mutableStateOf(generateRandomOrder().joinToString("").toInt()) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .offset(y = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {



        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            label = { Text("Inserisci la tua risposta") },

            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),


colors=TextFieldDefaults.outlinedTextFieldColors(
    textColor = MaterialTheme.colors.onSecondary,
    backgroundColor = MaterialTheme.colors.primary

)

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NumericButton("1") { appendToUserAnswer("1") }
            NumericButton("2") { appendToUserAnswer("2") }
            NumericButton("3") { appendToUserAnswer("3") }
            NumericButton("4") { appendToUserAnswer("4") }
        }
        Button(
            onClick = {
                val isCorrect = userAnswer.toInt() == correctResult
                val feedback = if (isCorrect) "Corretto!" else "Sbagliato. Riprova."
if (isCorrect)
{isAggressive=false
//isButtonEnabled=true
isQuiz=false}

                println(feedback)
                userAnswer=""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors=ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            )

        ) {
            Text("Verifica Risposta")
        }
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    shape = RoundedCornerShape(20),
                    color = Color.Green
                )
        ) {
            Text(text="L'ordine  di oggi è : $correctResult",
                color= Color.Black,
                modifier=Modifier
                    .background(Color.Green)
                    )
        }
    }
}
// gnerare risposta ordine casuale
fun generateRandomOrder(): List<Int> {
    // Creare una lista con i numeri da 1 a 4
    val numbers = (1..4).toList()

    // Mescolare la lista in modo casuale
    val ordineCasuale = numbers.shuffled()

    return ordineCasuale
}

fun showImpostedMessage(context: Context) {

    Toast.makeText(context, "sveglia impostata!", Toast.LENGTH_SHORT).show()
}
@Composable
fun NumericButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier

            .padding(4.dp),
        colors=ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.primary,
            containerColor = MaterialTheme.colors.onPrimary
        )

    ) {
        Text(text)
    }
}

fun appendToUserAnswer(number: String) {

    userAnswer += number
}

fun showAggressiveQuestion(context: Context) {

    var one=5;
    var two=3;

    Toast.makeText(context, "Domanda aggressiva!", Toast.LENGTH_SHORT).show()
}

fun showErrorMessage(context: Context) {
    // Gestisci l'errore quando l'orario della sveglia è nel passato
    Toast.makeText(context, "Errore: l'orario della sveglia è nel passato", Toast.LENGTH_SHORT).show()
}


@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MyTheme {
        MainScreen()
       // QuizContent()
    }
}