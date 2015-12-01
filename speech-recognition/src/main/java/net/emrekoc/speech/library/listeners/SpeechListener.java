package net.emrekoc.speech.library.listeners;

import net.emrekoc.speech.library.enums.ErrorType;

import java.util.ArrayList;

/**
 * Created by emrekoc on 25/11/15.
 */
public interface SpeechListener {
     void onSpeechRecognized(ArrayList<String> results);
     void onError(ErrorType errorType);
}
