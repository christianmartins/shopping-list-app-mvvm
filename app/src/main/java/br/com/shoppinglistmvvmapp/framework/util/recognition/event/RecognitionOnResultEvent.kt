package br.com.shoppinglistmvvmapp.framework.util.recognition.event

import br.com.shoppinglistmvvmapp.framework.util.recognition.RecognitionParams
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent

class RecognitionOnResultEvent(
    val bestResult: String,
    val recognitionParams: RecognitionParams?
): MessageEvent()
