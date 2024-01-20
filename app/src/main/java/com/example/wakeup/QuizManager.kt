package com.example.wakeup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

var userAnswer by   mutableStateOf("")
@Composable
fun QuizContent() {
    val context= LocalContext.current
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


            colors= TextFieldDefaults.outlinedTextFieldColors(
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
                if(userAnswer.isNullOrBlank()){
                    showErrorEmpty(context)

                }
                else {
                    val isCorrect = userAnswer.toInt() == correctResult
                    val feedback = if (isCorrect) "Corretto!" else "Sbagliato. Riprova."
                    if (isCorrect) {
                        isAggressive = false
//isButtonEnabled=true
                        isQuiz = false
                    }

                    println(feedback)
                    userAnswer = ""
                }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors= ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            )

        ) {
            Text("Verifica Risposta")
        }
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(20),
                    color = Color.Green
                )
        ) {
            Text(text="L'ordine  di oggi è : $correctResult",
                fontSize=20.sp,
                fontWeight= FontWeight.Bold,

                color= Color.Black,

                modifier= Modifier

                    .background(Color.Green)


            )
        }
    }
}

fun generateRandomOrder(): List<Int> {
    // create the list (1-4)
    val numbers = (1..4).toList()

    // list shuffle
    val ordineCasuale = numbers.shuffled()

    return ordineCasuale
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
