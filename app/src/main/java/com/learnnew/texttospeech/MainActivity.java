package com.learnnew.texttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView Text,speakText;
    Button btnAns,btnAgain;
    TextToSpeech textToSpeech;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    int min=1, max=10;
    int questions=20;
    int z;

    static int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Text = findViewById(R.id.readText);
        speakText = findViewById(R.id.speak_val);
        btnAns=findViewById(R.id.ans);
        btnAgain=findViewById(R.id.again);


            int a = (int) (Math.random() * (max - min + 1) + min);
            int b = (int) (Math.random() * (max - min + 1) + min);

            Text.setText(a + " * " + b);
            z = multiplication(a, b);
            System.out.println("Answer A : " + a);
            System.out.println("Answer B : " + b);
            System.out.println("Answer: " + z);

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.ENGLISH);
                    }

                    if (i == TextToSpeech.SUCCESS) {

                        System.out.println("Success");
                        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onDone(String utteranceId) {
                                Log.d("MainActivity", "TTS finished");

                                Intent intent
                                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");

                                try {
                                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                                } catch (Exception e) {
                                    Toast
                                            .makeText(MainActivity.this, " " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }

                            @Override
                            public void onError(String utteranceId) {
                            }

                            @Override
                            public void onStart(String utteranceId) {


                            }

                        });
                    } else {
                        Log.e("MainActivity", "Initilization Failed!");
                    }

                }


            });


            if (Text.getText().toString() != null) {


                new Handler().postDelayed(new Runnable() {
                    public void run() {


                        HashMap<String, String> myHashAlarm = new HashMap<String, String>();
                        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
                        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
                        textToSpeech.speak(Text.getText().toString(), TextToSpeech.QUEUE_FLUSH, myHashAlarm);

                    }

                }, 1500);


            }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        String ans;
        String correct = "Good Correct";
        String incorrect ="Sorry Incorrect";

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                ans = Objects.requireNonNull(result).get(0).toString();
                System.out.println("Answer Result : "+ ans);

                if (ans.equals(String.valueOf(z)))
                {

                    textToSpeech.speak(correct,TextToSpeech.QUEUE_FLUSH,null);
                    speakText.setText(ans);
                    speakText.setVisibility(View.VISIBLE);
                    btnAns.setText("Correct");
                    btnAns.setVisibility(View.VISIBLE);
                    taptoAnimate(btnAns);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                    if(count<questions) {
                        count++;
                        System.out.println("Answer count :"+ count);
                        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i1);
                        finish();

                    }
                    else
                    {
                        speakText.setVisibility(View.INVISIBLE);
                        textToSpeech.speak("Thank You",TextToSpeech.QUEUE_FLUSH,null);
                        Text.setText("Thank You!!");
                        btnAns.setText("Test Over!");
                        btnAgain.setVisibility(View.VISIBLE);
                        taptoAnimate(btnAgain);

                    }
                        }

                    }, 2000);


                }
                else
                {
                    textToSpeech.speak(incorrect,TextToSpeech.QUEUE_FLUSH,null);
                    speakText.setText(ans);
                    speakText.setVisibility(View.VISIBLE);
                    btnAns.setText("InCorrect");
                    btnAns.setBackgroundColor(getResources().getColor(R.color.red));
                    btnAns.setVisibility(View.VISIBLE);
                    taptoAnimate(btnAns);

                    System.out.println("Answer count :"+ count);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                    if(count< questions) {
                        count++;
                        System.out.println("Answer count :"+ count);
                        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i1);
                        finish();

                    }
                    else
                    {
                        speakText.setVisibility(View.INVISIBLE);
                        textToSpeech.speak("Thank You",TextToSpeech.QUEUE_FLUSH,null);
                        Text.setText("Thank You!!");
                        btnAns.setText("Test Over!");
                        btnAgain.setVisibility(View.VISIBLE);
                        taptoAnimate(btnAgain);

                    }
                        }

                    }, 2000);
                }

                btnAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count = 0;
                        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i1);
                        finish();

                    }
                });

            }
        }


    }

    public void taptoAnimate(Button btn){

        //Button button=(Button)findViewById(R.id.listen);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
        animation.setInterpolator(interpolator);
        btn.startAnimation(animation);

    }

    public int multiplication(int x,int y)
    {
        return Math.multiplyExact(x,y);
    }
}