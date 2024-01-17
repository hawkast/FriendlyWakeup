package com.example.wakeup

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
var imposted by mutableStateOf(false)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog() {
    val context = LocalContext.current
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.fillMaxWidth(),

        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.LightGray.copy(alpha = .3f))
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timeState,
                    colors=TimePickerDefaults.colors(

                            clockDialColor = Color.Green,
                            clockDialSelectedContentColor = Color.Red,
                                clockDialUnselectedContentColor = Color.Black,
                            selectorColor = Color.Black,
                            periodSelectorBorderColor = Color.Blue,
                            periodSelectorSelectedContainerColor = Color.Black,
                            periodSelectorSelectedContentColor = Color.Green,
                            periodSelectorUnselectedContainerColor = Color.White,
                            periodSelectorUnselectedContentColor = Color.Black,
                            timeSelectorSelectedContainerColor = Color.Black,
                            timeSelectorSelectedContentColor = Color.Green,

                    ))
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End,

                ) {
                    TextButton(onClick = { showDialog = false },
                       // shape = RoundedCornerShape(12.dp),
                     colors=ButtonDefaults.textButtonColors(

                         contentColor = MaterialTheme.colors.onPrimary,
                        // containerColor = MaterialTheme.colors.onPrimary,

                     )

                        ) {
                        Text(text = "indietro"
                          )
                    }
                    TextButton(onClick = {
                        imposted=true
                        showDialog = false

                        selectedHour = timeState.hour
                        selectedMinute = timeState.minute
                        setAl(selectedHour,selectedMinute, isAggressive, context )
                    },
                        colors=ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colors.onPrimary,
                        )
                        ) {
                        Text(text = "Conferma")
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
// colore per i button
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,


            ) ){
            Text(text = "IMPOSTA SVEGLIA")
        }
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    shape = RoundedCornerShape(20),
                    color = Color.Gray
                )
        ) {
            Text(
                text = "La sveglia è impostata alle  ${timeState.hour} : ${timeState.minute}",
                color = Color.Black,
                modifier = Modifier.padding(4.dp)
            )
        }

        Switch(
            checked = isAggressive,

            onCheckedChange = {
                if(imposted){
                   } else{
                isAggressive = it

                isButtonEnabled=false}

            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Pulsante per impostare l'allarme


        Button(
            onClick = {

                selectedMinute=0
                timeState.hour
                timeState.minute

                setAl(selectedHour,selectedMinute, isAggressive, context )

            },
            modifier = Modifier.fillMaxWidth(),

            enabled = false,
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
                imposted=false


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
    }

fun setAl(selectedHour: Int, selectedMinute: Int, isAggressive: Boolean, context: Context) {
    mediaPlayer = MediaPlayer.create(context, R.raw.alarm)

    val now = Calendar.getInstance()
    val alarmTimeCalendar = now.clone() as Calendar
    alarmTimeCalendar.set(Calendar.HOUR_OF_DAY, selectedHour)
    alarmTimeCalendar.set(Calendar.MINUTE, selectedMinute)
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
            if (!isAggressive && isAlarmRinging) {
                showRingMessage(context)
            } else {
                isQuiz = true
                showAggressiveQuestion(context)
            }
        }, timeDifferenceMillis)
    } else {

        showErrorMessage(context)
    }
}

@Composable
@Preview(showBackground = true)
fun Prev() {
    MyTheme {
        TimePickerWithDialog()
    }
}