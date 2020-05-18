package com.victoryzzi.android.apps.idus.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackBar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(
        this,
        msg,
        duration
    ).show()
}