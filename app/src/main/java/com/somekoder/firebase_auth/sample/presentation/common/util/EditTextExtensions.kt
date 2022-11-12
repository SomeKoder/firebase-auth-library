package com.somekoder.firebase_auth.sample.presentation.common.util

import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.setTextIfDifferent(text: String) {
    if (this.text.toString() != text) setText(text)
}