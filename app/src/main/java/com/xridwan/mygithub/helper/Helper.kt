package com.xridwan.mygithub.helper

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

object Helper {

    const val BASE_URL = "https://api.github.com/"
    const val TOKEN = "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"

    fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}