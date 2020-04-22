package hitesh.asimplegame;

/**
 * Created by H on 7/12/2015.
 */

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class QuestionActivity extends Activity {
    private static final String TAG = QuestionActivity.class.getSimpleName();       //getSimpleName() : 단순히 클래스 이름만을 가져옴
    private static int level;

    final private int INF = 99999;

    private List<Question> questionList;
    private int score = 0;
    private int questionID = 0;
    //효과음
    private SoundPool soundPool;
    private int soundID;
    private int vol;//볼륨, 추후 수정
    //setting
    private SharedPreferences sf;
    private Question currentQ;
    private TextView txtQuestion, times, scored,chance;
    private Button button1, button2, button3;

    //모드
    int life;//목숨기능
    boolean isLifeMode = true; //라이프모드인지

    //    private int level;
    CounterClass timer = new CounterClass(60000, 1000);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //해당 layout 가져옴
        QuizDBOpenHelper db = new QuizDBOpenHelper(this);  // my question bank class

        //=========================효과음============================//
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this,R.raw.button,1);

        questionList = db.getAllQuestions(level);  // this will fetch all quetonall questions
        currentQ = questionList.get(questionID); // the current question
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed

        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        button1 = (Button) findViewById(R.id.button1);          //정답 보기
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        //========================세팅 값============================//
        sf = getSharedPreferences("settings",MODE_PRIVATE);
        vol = sf.getInt("effect",1);
        chance = findViewById(R.id.chance);
        //========================모드 결정=========================//
        Toast.makeText(getApplicationContext(), "life : " + life, Toast.LENGTH_SHORT).show();

        if(sf.getBoolean("lifeMode",false)){
            life = 3;//목숨 3개
            chance.setText("Chance: "+ life); // 적용
            isLifeMode = true;
        }else if(sf.getBoolean("inifMode",false)){
            life = INF;
            chance.setText("무한대");
            isLifeMode =false;
        }else{
            life = 1;
            isLifeMode = false;
            chance.setText(" ");
        }

        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);

        // the timer
        times = (TextView) findViewById(R.id.timers);       //Id로 뷰 찾기


        // method which will set the things up for our game
        setQuestionView();
        times.setText("00:02:00");      //이건 왜 설정한거지?   //초기 설정 1분으로 되어있음

        // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
        timer.start();
        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundPool.play(soundID,vol,vol,0,0,0);//효과음 재생
                Toast.makeText(getApplicationContext(), "vol : " + vol, Toast.LENGTH_SHORT).show();
                // passing the button text to other method
                // to check whether the anser is correct or not
                // same for all three buttons
                getAnswer(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,vol,vol,0,0,0);
                getAnswer(button2.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID,vol,vol,0,0,0);
                getAnswer(button3.getText().toString());
            }

        });

    }

    public static void setLevel(int lv){
        level = lv;
    }

    public static int getLevel(){
        return level;
    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {        //정답일때

            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;        //score 0인데 왜 화면에는 1로 시작?           //정답일때 스코어가 올라가는데 왜 처음이 1인가?
            scored.setText("Score : " + score);
        } else {
            if(life<=1){
                // if unlucky start activity and finish the game
                Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                timer.cancel();
                // passing the int value
                Bundle b = new Bundle();
                b.putInt("score", score); // Your score
                intent.putExtras(b); // Put your score to your next
                startActivity(intent);
                finish();
            }else{
                life--;
            }
            if(isLifeMode ==true) chance.setText("Chance : "+life); //목숨 모드일 때
        }

        if (questionID < QuizDBOpenHelper.getSize()) {          //최대 20문제인가봄(0~19 -> 20문제)
            // if questions are not over then do this
            currentQ = questionList.get(questionID);        //현재 질문에 질문리스트에서의 ID(번호) 가지고 오기
            setQuestionView();
        } else {
            timer.cancel();
            // if over do this
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }

    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")     //상위버전의 SDK의 API를 사용할때 warning을 없애고 개발자가 해당 API사용 가능
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            times.setText("Time is up");
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {      //타이머 이벤트 발생
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d",        //남은 시간 표기 포맷
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));

            Log.d(TAG, "current time: " + hms);     //디버그->싱태확인용
            times.setText(hms);     //남은 시간 표기
        }

    }
    private void setQuestionView() {        //문제 뷰
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());

        questionID++;
    }


}
