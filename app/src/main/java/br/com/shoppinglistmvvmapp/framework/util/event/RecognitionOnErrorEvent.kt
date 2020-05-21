package br.com.shoppinglistmvvmapp.framework.util.event

import androidx.annotation.StringRes
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent

class RecognitionOnErrorEvent(
    @StringRes
    val errorMessageStringRes: Int
): MessageEvent()
