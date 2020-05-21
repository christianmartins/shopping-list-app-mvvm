package br.com.shoppinglistmvvmapp.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import br.com.shoppinglistmvvmapp.framework.App
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.framework.util.speak.SpeakUtils
import java.util.*

class RecognitionUtils {
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        initSpeechRecognizer()
    }

    private fun getInstanceSpeechRecognizer(): SpeechRecognizer?{
        return App.context?.applicationContext?.let {
            SpeechRecognizer.createSpeechRecognizer(
                it,
                ComponentName.unflattenFromString(GlobalUtils.googleRecognitionServiceName)
            )
        }
    }

    private fun initSpeechRecognizer(){
        if(speechRecognizer == null){
            speechRecognizer = getInstanceSpeechRecognizer()
        }
    }

     private fun getSpeechIntent(context: Context): Intent{
         // for more: https://stackoverflow.com/questions/7973023/what-is-the-list-of-supported-languages-locales-on-android

//        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,  5000L)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 5000L)
//        speechIntent.putExtra("android.speech.extra.DICTATION_MODE", false)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)


         return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).let {
             it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
             it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
             it.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
             it.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
         }
     }


    fun startRecognition(recognitionParams: RecognitionParams? = null){
        App.context?.applicationContext?.let {
            if (SpeechRecognizer.isRecognitionAvailable(it)) {
                initSpeechRecognizer()
                speechRecognizer?.setRecognitionListener(RecognitionListener(recognitionParams))
                speechRecognizer?.startListening(getSpeechIntent(it))
            } else {
                SpeakUtils.dispatchOnErrorEvent(R.string.speech_error_could_not_start_speech_recognizer)
            }
        }
    }

    fun stopRecognition(){
        speechRecognizer?.cancel()
    }
}