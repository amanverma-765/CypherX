package com.akv.cypherx.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(
    message: String?,
    lengthShort: Boolean = false
) {
    Toast.makeText(
        this,
        message,
        if (lengthShort) Toast.LENGTH_SHORT
        else Toast.LENGTH_LONG
    ).show()
}