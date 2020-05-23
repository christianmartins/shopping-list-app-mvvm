package br.com.shoppinglistmvvmapp.framework.util.recognition.event

import androidx.annotation.StringRes
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent

class RecognitionOnErrorEvent(
    @StringRes
    val errorMessageStringRes: Int
): MessageEvent()
