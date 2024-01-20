package com.example.wakeup

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.AlertDialog
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
    var mytext by  remember {mutableStateOf(" la sveglia è impostata alle ${timeState.hour}:${timeState.minute}")}
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

                     colors=ButtonDefaults.textButtonColors(

                         contentColor = MaterialTheme.colors.onPrimary,


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
                        showImpostedMessage(context)
                        mytext="la sveglia è impostata alle ${timeState.hour} : ${timeState.minute}"
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

            enabled = enabledSetAlarm,
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
                    color = Color.DarkGray
                )
        ) {
            Text(

                text = mytext,

                color = Color.Green,
                modifier = Modifier.padding(4.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
                    .background(
                        shape = RoundedCornerShape(20),
                        color = Color.DarkGray

                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Modalità Aggressiva:",
                        modifier = Modifier.weight(1f),
                        color= Color.Green
                    )
                    Switch(
                        checked = isAggressive,
                        onCheckedChange = {
                            if (imposted) {

                            } else {
                                isAggressive = it
                               // isButtonEnabled = it
                              //  enabledDeletButton=it
                            }
                        },
                        modifier = Modifier.padding(bottom = 16.dp),
                        colors= SwitchDefaults.colors(  checkedThumbColor = Color.Green,  // Colore del pollice quando lo switch è attivo
                            checkedTrackColor = Color.Green.copy(alpha = 0.5f),  // Colore della traccia quando lo switch è attivo
                            uncheckedThumbColor = Color.Gray,  // Colore del pollice quando lo switch è disattivo
                            uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f))

                    )
                }
            }}


        // Button to set alarm


        Button(
            onClick = {
                if(!imposted)
                {
                 showErrorImpostedMessage(context)
                }
                else {
                    imposted = false
                    removecall()
                    showDeletedMessage(context)
                    selectedHour=0;
                    selectedMinute=0;
mytext=" la sveglia è impostata alle ${selectedHour} : ${selectedMinute}"
                } },
            modifier = Modifier.fillMaxWidth(),

            enabled = enabledDeletButton,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,

                ),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 8.dp)
        ) {
            Text("Cancella sveglia")
        }

        // button to stop alarm
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
    enabledDeletButton=!isAggressive
    enabledSetAlarm=!isAggressive&& imposted
    }


//ManagerAlarm

fun setAl(selectedHour: Int, selectedMinute: Int, isAggressive: Boolean, context: Context) {
    mediaPlayer = MediaPlayer.create(context, R.raw.alarm)

    val now = Calendar.getInstance()
    val alarmTimeCalendar = now.clone() as Calendar
    alarmTimeCalendar.set(Calendar.HOUR_OF_DAY, selectedHour)
    alarmTimeCalendar.set(Calendar.MINUTE, selectedMinute)
    alarmTimeCalendar.set(Calendar.SECOND, 0)
    alarmTimeCalendar.set(Calendar.MILLISECOND, 0)


    if (alarmTimeCalendar.timeInMillis <= now.timeInMillis) {
        showErrorMessage(context)
        return
    }
    val timeDifferenceMillis = alarmTimeCalendar.timeInMillis - now.timeInMillis

    if (timeDifferenceMillis > 0) {
        // set alarm


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
    }
}
fun removecall(){ handler.removeCallbacksAndMessages(null)}


fun stopAlarm() {
    // Check if the MediaPlayer is initialized and playing
    mediaPlayer?.let {
        if (it.isPlaying) {
            it.stop()
            it.release()
            mediaPlayer = null
        }
    }
}




@Composable
@Preview(showBackground = true)
fun Prev() {
    MyTheme {
        TimePickerWithDialog()
    }
}