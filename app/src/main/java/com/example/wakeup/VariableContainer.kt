package com.example.wakeup

import android.media.MediaPlayer
import android.os.Handler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var mediaPlayer: MediaPlayer? = null  // variable to manage the alarm
var isButtonEnabled by  mutableStateOf(true) // off button
var isAlarmRinging by mutableStateOf(false) // variable to track ringing
var isAggressive by mutableStateOf(false) // variable to set aggressive mode
var isQuiz by mutableStateOf(false) // manage quiz
val handler = Handler() // to stop/play alarm
var enabledDeletButton by   mutableStateOf(true) // off/on delete button
var imposted by mutableStateOf(false) //to  track alarm imposted
var enabledSetAlarm by mutableStateOf(true)  // on/off setAlarm button
var userAnswer by   mutableStateOf("") // to manage the user answer