package br.com.shoppinglistmvvmapp.framework.util.speak

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.annotation.StringRes
import br.com.shoppinglistmvvmapp.utils.DateUtils
import br.com.shoppinglistmvvmapp.framework.util.recognition.event.RecognitionOnErrorEvent
import org.greenrobot.eventbus.EventBus
import java.util.*

class SpeakUtils(private val context: Context): TextToSpeech.OnInitListener {

    private var textToSpeech = getTextToSpeechInstance()

    private fun getTextToSpeechInstance(): TextToSpeech {
        return TextToSpeech(context, this).let {
            it.language = Locale.getDefault()
            it.setSpeechRate(1.8F)
            it
        }
    }

    fun speak(charSequence: CharSequence, onSpeakDone:(() ->Unit)? = null, params: Bundle? = null){
        try{
            textToSpeech.speak(charSequence, TextToSpeech.QUEUE_FLUSH, params, DateUtils.getTimeStamp().toString())
            textToSpeech.setOnUtteranceProgressListener(SpeakCustomUtteranceProgress(onSpeakDone))
            textToSpeech.Engine()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun stop(){
        textToSpeech.stop()
    }

    override fun onInit(p0: Int) {
        println( "${this.javaClass.name} - onInit: "+ if(p0 == TextToSpeech.SUCCESS)"success" else "failed")
    }

    companion object {
        fun dispatchOnErrorEvent(@StringRes errorMessage: Int){
            EventBus.getDefault().post(
                RecognitionOnErrorEvent(
                    errorMessage
                )
            )
        }
    }

}

