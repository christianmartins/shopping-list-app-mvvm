package br.com.shoppinglistmvvmapp.framework.util.speak

import android.speech.tts.UtteranceProgressListener

class SpeakCustomUtteranceProgress(private val onSpeakDone:(() ->Unit)? = null): UtteranceProgressListener(){

    override fun onDone(p0: String?) {
        onSpeakDone?.invoke()
    }

    override fun onError(p0: String?) {}

    override fun onStart(p0: String?) {}

}