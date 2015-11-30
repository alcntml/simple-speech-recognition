package net.emrekoc.speech.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;


import net.emrekoc.speech.library.listeners.SpeechListener;

import java.util.ArrayList;

/**
 * Created by emrekoc on 25/11/15.
 */
public class SimpleRecognizer implements RecognitionListener {
    String TAG = SimpleRecognizer.class.getSimpleName();
    static SimpleRecognizer speechRecognition;
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
        Log.i(TAG, "onError");
        if (listener!=null){
            listener.onError();
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
