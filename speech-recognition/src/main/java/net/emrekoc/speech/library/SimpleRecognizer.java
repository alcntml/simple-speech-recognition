package net.emrekoc.speech.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import net.emrekoc.speech.library.enums.ErrorType;
import net.emrekoc.speech.library.listeners.SpeechListener;

import java.util.ArrayList;

/**
 * Created by emrekoc on 25/11/15.
 */
public class SimpleRecognizer implements RecognitionListener {
    String TAG = SimpleRecognizer.class.getSimpleName();
    private SpeechRecognizer recognizer;
    private SpeechListener listener;

    private SimpleRecognizer() {
    }

    public SimpleRecognizer(Context context) {
        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizer.setRecognitionListener(this);
    }

    public void setOnVoiceRecognizedListener(SpeechListener listener) {
        this.listener = listener;
    }

    public void startRecognizing(SpeechListener listener) {
        setOnVoiceRecognizedListener(listener);
        startRecognizing();
    }


    public void destroy(){
        recognizer.destroy();
        Log.d(TAG, "destroy");
    }

    public void startRecognizing(Intent recognizingIntent) {
        if (!isSpeechListenerExists()) {
            return;
        }
        recognizer.startListening(recognizingIntent);
    }


    /**
     * You need to call setOnVoiceRecognizedListener method before calling startRecognizing().
     */
    public void startRecognizing() {
        if (!isSpeechListenerExists()) {
            return;
        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "tr-TR");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        recognizer.startListening(intent);
        Log.d(TAG ,"startRecognizing");
    }


    private boolean isSpeechListenerExists() {
        if (listener == null) {
            Log.e(TAG, "You must call setOnVoiceRecognizedListener method before calling startRecognizing() method");
            return false;
        }
        return true;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(TAG, "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        recognizer.cancel();
        if (listener!=null){
            ErrorType errorType = null;
            switch (error){
                /**NETWORK TIMEOUT ERROR**/
                case 1:
                    errorType = ErrorType.NETWORK_TIMEOUT_ERROR;
                    break;
                 /**NETWORK ERROR**/
                case 2:
                    errorType = ErrorType.NO_CONNECTION_ERROR;
                    break;
                 /**AUDIO RECORDING ERROR**/
                case 3:
                    errorType = ErrorType.AUDIO_RECORDING_ERROR;
                    break;
                 /**SERVER ERROR. ALSO SHOULD BE CONNECTION PROBLEM**/
                case 4:
                    errorType = ErrorType.SERVER_ERROR;
                    break;
                 /**CLIENT SIDE ERROR**/
                case 5:
                    errorType = ErrorType.CLIENT_ERROR;
                    break;
                 /**SPEECH TIMEOUT**/
                case 6:
                    errorType = ErrorType.SPEECH_TIMEOUT_ERROR;
                    break;
                 /**NO MATCH**/
                case 7:
                    errorType = ErrorType.NO_MATCH_ERROR;
                    break;
                 /**RECOGNIZER BUSY**/
                case 8:
                    errorType = ErrorType.RECOGNIZER_BUSY_ERROR;
                    break;
                 /**INSUFFICIENT PERMISSIONS**/
                case 9:
                    errorType = ErrorType.INSUFFICIENT_PERMISSIONS_ERROR;
                    break;
                default:
                    errorType = ErrorType.UNKNOWN;
                    break;
            }
            Log.i(TAG, "onError: "+error);
            listener.onError(errorType);
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(TAG, "onResults");
        ArrayList<String> arrayList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (listener != null) {
            listener.onSpeechRecognized(arrayList);
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(TAG, "onEvent");
    }
}
