package br.com.shoppinglistmvvmapp.utils.event

import androidx.annotation.StringRes

class RecognitionOnErrorEvent(
    @StringRes
    val errorMessageStringRes: Int
): MessageEvent()
