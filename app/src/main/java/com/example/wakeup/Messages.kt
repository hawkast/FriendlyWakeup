package com.example.wakeup

import android.content.Context
import android.widget.Toast

fun showRingMessage(context: Context) {


    Toast.makeText(context, "Sta suonando!", Toast.LENGTH_SHORT).show()

}

    fun showAggressiveQuestion(context: Context) {
        Toast.makeText(context, "Domanda aggressiva!", Toast.LENGTH_SHORT).show()
    }

    fun showErrorMessage(context: Context) {

        Toast.makeText(context, "Errore: verifica che AM/PM siano impostati correttamente!", Toast.LENGTH_SHORT).show()
    }
    fun showImpostedMessage(context: Context) {

        Toast.makeText(context, "sveglia impostata!", Toast.LENGTH_SHORT).show()
    }
    fun showDeletedMessage(context: Context) {

        Toast.makeText(context, "sveglia cancellata!", Toast.LENGTH_SHORT).show()
    }
    fun showErrorEmpty(context: Context) {

        Toast.makeText(context, "Campo vuoto!!", Toast.LENGTH_SHORT).show()
    }
    fun showErrorImpostedMessage(context: Context) {

        Toast.makeText(context, "nessuna sveglia impostata!!", Toast.LENGTH_SHORT).show()
    }

