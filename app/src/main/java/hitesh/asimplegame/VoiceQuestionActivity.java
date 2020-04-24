package hitesh.asimplegame;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VoiceQuestionActivity extends Activity {

//    final private String SUCCESS  ="축하합니다! 정답입니다";
//    final private String FAILED ="틀렸습니다 다시 시도해보세요~";

    final private int MAX = 4;

    private TextView result, quetitle;
    private String title = "Question";
    private String answer = "";
    private int score = 0; // 결과창으로 넘길 것
    private int questionID = 0;
    private int order = 1;
    private List<VoiceQuestion> questionList;
    VoiceQuestion currentQ;
    //효과음
    private SoundPool soundPool;
    int soundID;
    int vol = 1;
    //setting
    private SharedPreferences sf;
    //STT
    Intent intent;
    SpeechRecognizer speech;
    final int PERMISSION = 1;
    //TTS
    private final Bundle params = new Bundle();
    private TextToSpeech tts;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        //permission check
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        //setting
        sf = getSharedPreferences("settings", MODE_PRIVATE);
        vol = sf.getInt("effect", 1);
        //TITLE
        result = findViewById(R.id.stt_result);
        quetitle = findViewById(R.id.title);
        //효과음
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.button, 1);

        //DB
        QuizDBOpenHelper db = new QuizDBOpenHelper(this);
        questionList = db.getAllVoiceQuestions();  // this will fetch all quetonall questions
        currentQ = questionList.get(questionID);
        //문제
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(listener);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ko-KR");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        initTTS();
    }

    public void answer(View o) { // 문제 답변
        soundPool.play(soundID, vol, vol, 0, 0, 0);
        speech.startListening(intent);
    }

    public void next(View o) {
        soundPool.play(soundID, vol, vol, 0, 0, 0);

        if (currentQ.getANSWER().equals(answer)) score++;

        title = "Question  " + (order + questionID + 1);//타이틀 번호 설정

        if (questionID >= MAX) {
            Intent intent = new Intent(VoiceQuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            finish();
            startActivity(intent);

        } else {
            questionID++;//다음 문제 넘어가기
            currentQ = questionList.get(questionID);
            quetitle.setText(title);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void question(View o) { // 문제 출력
        soundPool.play(soundID, vol, vol, 0, 0, 0);
        speakTTS(currentQ.getQUESTION());
    }

    //<============STT result : message, voice -> text
    private final RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Toast.makeText(getApplicationContext(), "음성 인식 시작합니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            Toast.makeText(getApplicationContext(), "완료", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int i) {
            String message;

            switch (i) {

                case SpeechRecognizer.ERROR_AUDIO:
                    message = "ERROR_AUDIO";
                    break;

                case SpeechRecognizer.ERROR_CLIENT:
                    message = "ERROR_CLIENT";
                    break;

                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "ERROR_INSUFFICIENT_PERMISSIONS";
                    break;

                case SpeechRecognizer.ERROR_NETWORK:
                    message = "ERROR_NETWORK";
                    break;

                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "ERROR_NETWORK_TIMEOUT";
                    break;

                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "다시 말해주세요";
                    break;

                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "ERROR_RECOGNIZER_BUSY";
                    break;

                case SpeechRecognizer.ERROR_SERVER:
                    message = "ERROR_SERVER";
                    break;

                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "ERROR_SPEECH_TIMEOUT";
                    break;

                default:
                    message = "UNKNOWN";
                    break;
            }

            Log.e("GoogleActivity", "SPEECH ERROR : " + message);
            Toast.makeText(getApplicationContext(), "ERROR : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> res = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            answer = "";//초기화
/*            for (int i = 0; i < res.size(); i++) {
                answer += res.get(i);
            }*/
            answer = res.get(0);//결과가 붙어서 출력되서 일단은 앞만 인식
            result.setText(answer);
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
    //======>

    //<=====TTS text -> voice
    private void initTTS() {
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int state) {
                if (state == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR WHILE GENERATING OBJ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //LOLLIPOP 이상의 버전은 필요함으로
    private void speakTTS(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, text);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tts.stop();
        tts.shutdown();
        super.onDestroy();
    }
//    @Override
//    public void onClick(View view) {
//    }
    //=====>
}
