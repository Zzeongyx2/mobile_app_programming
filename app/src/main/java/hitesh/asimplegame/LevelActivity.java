package hitesh.asimplegame;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;


public class LevelActivity extends Activity implements View.OnClickListener {

    //    int level=0;
    private SoundPool soundPool;
    private int soundID;
    private int vol;//볼륨, 추후 수정
    private SharedPreferences sf;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        //setting
        sf = getSharedPreferences("settings",MODE_PRIVATE);
        vol = sf.getInt("effect",1);
        //효과음
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this,R.raw.button,1);

        Button btn_e = (Button) findViewById(R.id.btn_easy);
        btn_e.setOnClickListener(this);
        Button btn_m = (Button) findViewById(R.id.btn_medium);
        btn_m.setOnClickListener(this);
        Button btn_h = (Button) findViewById(R.id.btn_hard);
        btn_h.setOnClickListener(this);
    }

    public void voice(View o) {
        vol = sf.getInt("effect", 1);
        soundPool.play(soundID, vol, vol, 0, 0, 0);
        Intent intent = new Intent(this, VoiceQuestionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Bundle b = getIntent().getExtras();
        int mode = b.getInt("mode");
        if(mode == 1) {
            QuizDBOpenHelper.setSize(10);
            Intent intent = new Intent(this, QuestionActivity.class);
            switch (v.getId()) {
                case R.id.btn_easy:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(1);
                    break;
                case R.id.btn_medium:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(2);
                    break;
                case R.id.btn_hard:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(3);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
        if(mode == 2){
            QuizDBOpenHelper.setSize(500);
            Intent intent = new Intent(this, InfinityActivity.class);
            switch (v.getId()) {
                case R.id.btn_easy:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(1);
                    break;
                case R.id.btn_medium:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(2);
                    break;
                case R.id.btn_hard:
                    soundPool.play(soundID, vol, vol, 0, 0, 0);
                    QuestionActivity.setLevel(3);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    }



}