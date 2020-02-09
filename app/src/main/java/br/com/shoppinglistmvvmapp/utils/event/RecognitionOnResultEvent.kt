package br.com.shoppinglistmvvmapp.utils.event

import br.com.shoppinglistmvvmapp.utils.RecognitionParams

class RecognitionOnResultEvent(
    val bestResult: String,
    val recognitionParams: RecognitionParams?
): MessageEvent()
