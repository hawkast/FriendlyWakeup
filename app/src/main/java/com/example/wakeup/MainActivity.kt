package com.example.wakeup

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import android.media.MediaPlayer

import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import android.os.Handler

import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField

import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.material.lightColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
//import androidx.compose.material.Text


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType


import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.util.*
val eee: Colors = lightColors(
    primary = Color.Red,
    secondary = Color(0xFFFFC107),
    // Altri colori di tua scelta
    onPrimary = Color.Black, // Colore del testo su colore primario
    onSecondary = Color.Black, // Colore del testo su colore secondario
    onBackground = Color.Black, // Colore del testo su sfondo
    onSurface = Color.Black // Colore del testo su superficie
)



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
    var alarmTime by remember { mutableStateOf("12:00") }
   // var isAggressive by remember { mutableStateOf(false) }
    // tracciare il sta suonando

    val context = LocalContext.current
if(isAggressive==true && isQuiz==true){ QuizContent()}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
        )

        // Impostazione dell'opzione aggressiva

        Switch(
            checked = isAggressive,

            onCheckedChange = {
                isAggressive = it
                isAggressive=true
                isButtonEnabled=false
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Pulsante per impostare l'allarme


        Button(
            onClick = {
           setAlarm(alarmTime,isAggressive,context)
                showImpostedMessage(context)
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
            Text("imposta sveglia")
        }

        // Pulsante per spegnere l'allarme
        Button(
            onClick = {

                if( isAggressive==false){
                    isButtonEnabled=true
                isAlarmRinging = false
                stopAlarm()
                }
               // else if (isAggressive==true){ isButtonEnabled=false}
            },
            enabled= isButtonEnabled,
            modifier = Modifier.fillMaxWidth()


        ) {
            Text("Spegni Sveglia")
        }

    }
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

@Composable
fun QuizContent() {
    // Valori prefissati di 'a' e 'b'
    val aValue = 3.0
    val bValue = 5.0

    // Risultato corretto
    val correctResult = 8.0

    var userAnswer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Domanda
        Text("Qual è il risultato di $aValue + $bValue?")

        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            label = { Text("Inserisci la tua risposta") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                val isCorrect = userAnswer.toDoubleOrNull() == correctResult
                val feedback = if (isCorrect) "Corretto!" else "Sbagliato. Riprova."
if (isCorrect)
{isAggressive=false
isButtonEnabled=true
isQuiz=false}

                println(feedback)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Verifica Risposta")
        }
    }
}
fun showImpostedMessage(context: Context) {

    Toast.makeText(context, "sveglia impostata!", Toast.LENGTH_SHORT).show()
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
    }
}