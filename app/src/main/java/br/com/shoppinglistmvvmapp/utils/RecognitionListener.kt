package br.com.shoppinglistmvvmapp.utils

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.framework.util.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.framework.util.speak.SpeakUtils
import org.greenrobot.eventbus.EventBus


class RecognitionListener(private val recognitionParams: RecognitionParams? = null): RecognitionListener {

    override fun onResults(results: Bundle) {
        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.let {data ->
            if(data.size > 0){
                val bestResult = data[0].toString().trim()
                EventBus.getDefault().post(
                    RecognitionOnResultEvent(
                        bestResult,
                        recognitionParams
                    )
                )
            }
        }
    }

    override fun onError(error: Int) {
        SpeakUtils.dispatchOnErrorEvent(
            when (error) {
                SpeechRecognizer.ERROR_AUDIO -> R.string.speech_error_audio
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> R.string.speech_error_insufficient_permissions
                SpeechRecognizer.ERROR_NO_MATCH -> R.string.speak_not_understand
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> R.string.speech_error_recognition_service_busy
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> R.string.speak_not_understand
                (
                    SpeechRecognizer.ERROR_NETWORK or
                    SpeechRecognizer.ERROR_CLIENT or
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT or
                    SpeechRecognizer.ERROR_SERVER
                ) -> R.string.speech_error_network_and_client_and_timout_and_server
                else -> R.string.speak_not_understand
            }
        )
    }

    override fun onReadyForSpeech(params: Bundle?) {}

    override fun onBeginningOfSpeech() {}

    override fun onRmsChanged(rmsdB: Float) {}

    override fun onBufferReceived(buffer: ByteArray?) {}

    override fun onEndOfSpeech() {}

    override fun onPartialResults(partialResults: Bundle) {}

    override fun onEvent(eventType: Int, params: Bundle?) {}
}