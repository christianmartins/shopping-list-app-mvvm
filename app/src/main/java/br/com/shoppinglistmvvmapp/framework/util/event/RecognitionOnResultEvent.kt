package br.com.shoppinglistmvvmapp.framework.util.event

import br.com.shoppinglistmvvmapp.utils.RecognitionParams
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent

class RecognitionOnResultEvent(
    val bestResult: String,
    val recognitionParams: RecognitionParams?
): MessageEvent()
