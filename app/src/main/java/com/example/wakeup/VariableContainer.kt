package com.example.wakeup

import android.media.MediaPlayer
import android.os.Handler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var mediaPlayer: MediaPlayer? = null
var isButtonEnabled by  mutableStateOf(true)
var isAlarmRinging by mutableStateOf(false)
var isAggressive by mutableStateOf(false)
var isQuiz by mutableStateOf(false)
val handler = Handler()
var enabledDeletButton by   mutableStateOf(true)
var imposted by mutableStateOf(false)
var enabledSetAlarm by mutableStateOf(true)