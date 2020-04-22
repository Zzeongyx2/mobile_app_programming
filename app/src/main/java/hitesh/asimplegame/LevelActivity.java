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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        //setting
        SharedPreferences sf = getSharedPreferences("settings",MODE_PRIVATE);
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

    @Override
    public void onClick(View v) {
//        QuizDBOpenHelper db = new QuizDBOpenHelper(this);
//        Bundle b = new Bundle();
        Intent intent = new Intent(this, QuestionActivity.class);

        switch(v.getId()){
            case R.id.btn_easy:
                soundPool.play(soundID,vol,vol,0,0,0);
                QuestionActivity.setLevel(1);
//                b.putInt("level",1);
                break;
            case R.id.btn_medium:
                soundPool.play(soundID,vol,vol,0,0,0);
                QuestionActivity.setLevel(2);
//                db.setLevel(2);
//                b.putInt("level",2);
                break;
            case R.id.btn_hard:
                soundPool.play(soundID,vol,vol,0,0,0);
                QuestionActivity.setLevel(3);
//                db.setLevel(3);
//                b.putInt("level",3);
                break;

            default:
                break;
        }
//        intent.putExtras(b);
        startActivity(intent);
    }
    public void voice(View o) {
        soundPool.play(soundID,vol,vol,0,0,0);
        Intent intent = new Intent(this, VoiceQuestionActivity.class);
        startActivity(intent);
        finish();
    }
    //    public void easy(View o) {
////        setLevel(0);
//        Intent intent = new Intent(this, QuestionActivity.class);
//        intent.putExtra("level",0);
//        startActivity(intent);
//    }
//    public void medium(View o) {
//        Intent intent = new Intent(this, QuestionActivity.class);
//        intent.putExtra("level",1);
//        startActivity(intent);
//    }
//    public void hard(View o) {
////        setLevel(2);
//        Intent intent = new Intent(this, QuestionActivity.class);
//        intent.putExtra("level",2);
//        startActivity(intent);
//    }

//    public void setLevel(int lv){
//        level = lv;
//    }
//
//    public int getLevel(){
//        return level;
//    }

}