package br.com.shoppinglistmvvmapp.view.fragment

import android.os.Bundle
import androidx.annotation.StringRes
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.RecognitionParams
import br.com.shoppinglistmvvmapp.utils.RecognitionUtils
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent
import br.com.shoppinglistmvvmapp.utils.event.RecognitionOnErrorEvent
import br.com.shoppinglistmvvmapp.utils.event.RecognitionOnResultEvent
import br.com.shoppinglistmvvmapp.utils.speak.SpeakUtils

abstract class BaseSpeakAndRecognitionFragment: BaseFragment(){

    private val recognitionUtils = RecognitionUtils()
    private var speakUtils: SpeakUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        context?.let { speakUtils = SpeakUtils(it) }
        super.onCreate(savedInstanceState)
    }

    override fun onMessageEvent(event: MessageEvent) {
        super.onMessageEvent(event)
        if(GlobalUtils.fragmentAlive == this@BaseSpeakAndRecognitionFragment.javaClass.name) {
            when (event) {
                is RecognitionOnResultEvent -> {
                    onRecognitionOnResultEvent(event)
                }

                is RecognitionOnErrorEvent -> {
                    onRecognitionOnErrorEvent(event)
                }
            }
        }
    }

    abstract fun onRecognitionOnResultEvent(event: RecognitionOnResultEvent)

    abstract fun onRecognitionOnErrorEvent(event: RecognitionOnErrorEvent)

    fun speakOkAndMore(@StringRes stringRes: Int, onSpeakDone:(() -> Unit)? = null, params: Bundle? = null){
        speakOk(onSpeakDone = {
            speak(
                stringRes,
                onSpeakDone,
                params
            )
        })
    }

    fun speakOk(onSpeakDone:(() -> Unit)? = null, params: Bundle? = null){
        speak(R.string.speak_ok, onSpeakDone, params)
    }

    protected fun speak(@StringRes stringRes: Int, onSpeakDone:(() -> Unit)? = null, params: Bundle? = null){
        activity?.runOnUiThread {
            getWeakMainActivity().get()?.checkAudioRecordPermission()
            speakUtils?.speak(getString(stringRes), onSpeakDone, params)
        }
    }

    fun startRecognition(recognitionParams: RecognitionParams? = null){
        activity?.runOnUiThread {
            recognitionUtils.startRecognition(recognitionParams)
        }
    }

    fun stopAll(){
        stopRecognition()
        stopSpeak()
    }

    private fun stopSpeak(){
        activity?.runOnUiThread {
            speakUtils?.stop()
        }
    }

    private fun stopRecognition(){
        activity?.runOnUiThread {
            recognitionUtils.stopRecognition()
        }
    }

    override fun onPause() {
        super.onPause()
        stopAll()
    }
}