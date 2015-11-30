package sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.emrekoc.speech.library.SimpleRecognizer;
import net.emrekoc.speech.library.listeners.SpeechListener;

import java.util.ArrayList;

import speech.net.emrekoc.speechrecognition.R;

/**
 * Created by emrekoc on 30/11/15.
 */
public class SampleActivity extends Activity {

    Button btnStartListening;
    SimpleRecognizer recognizer;
    private ListView lvSpeechResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        recognizer = new SimpleRecognizer(this);

        recognizer.setOnVoiceRecognizedListener(new SpeechListener() {
            @Override
            public void onSpeechRecognized(ArrayList<String> results) {
                lvSpeechResults.setAdapter(new ArrayAdapter<String>(SampleActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,results));
            }

            @Override
            public void onError() {
                lvSpeechResults.setAdapter(null);
                Toast.makeText(SampleActivity.this,"Error: Try Again",Toast.LENGTH_LONG).show();
            }
        });

        btnStartListening = (Button) findViewById(R.id.btn_start_listening);
        lvSpeechResults = (ListView) findViewById(R.id.btn_speech_result);
        btnStartListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.startRecognizing();
            }
        });


    }

    @Override
    protected void onDestroy() {
        recognizer.destroy();
        super.onDestroy();
    }
}
