package hitesh.asimplegame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

/*처음에 볼륨키를 넣었는데 굳이 필요 없을 것 같아서 그냥 지우고 ON/OFF만 넣었습니다*/
/*MediaPlayer는 배경음악 처리 soundPool은 효과음 처리입니다*/

public class Settings extends Activity {
    private static MediaPlayer mp;

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    Switch life, effect, bgm, inif; //모드 추가하려면 더 해도..

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        life = findViewById(R.id.btn_life);
        bgm = findViewById(R.id.btn_bgm);
        effect = findViewById(R.id.btn_effect);
        inif = findViewById(R.id.btn_inif);
        //===============상태저장=================//
        sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        //===============상태 반영================//
        if(sharedPref.getInt("effect",1)==0) effect.setChecked(false);//초기 설정 값과 다르다면
        life.setChecked(sharedPref.getBoolean("lifeMode",false));
        bgm.setChecked(sharedPref.getBoolean("bgm",false));
        inif.setChecked(sharedPref.getBoolean("inifMode",false));

        //==================배경음악==============//
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.background);
            mp.setLooping(true);
        }
    }
    public void bgm(View o) {
        if (bgm.isChecked()) {
            mp.start();
            editor.putBoolean("bgm", true);
        }else if(mp.isPlaying()){
            mp.pause();
            editor.putBoolean("bgm", false);
        }
        editor.commit();
    }

    public void effect(View o) {
        if (effect.isChecked()) {
            editor.putInt("effect", 1);
        } else {
            editor.putInt("effect", 0);
        }
        editor.commit();
        // Toast.makeText(getApplicationContext(), "vol : " + sharedPref.getInt("effect",1), Toast.LENGTH_SHORT).show();//debug
    }

    public void lifeMode(View o) {
        if (life.isChecked()) {
            editor.putInt("mode", 1);
            editor.putBoolean("lifeMode", true);
            editor.putBoolean("inifMode",false);//목숨모드일 경우 자동으로 무한모드 OFF
            inif.setChecked(false);
        } else {
            editor.putBoolean("lifeMode", false);
        }
        editor.commit();
    }
    public void inifMode(View o) {
        if (inif.isChecked()) {
            editor.putInt("mode", 2);
            editor.putBoolean("inifMode", true);
            editor.putBoolean("lifeMode",false);//무한 모드일 경우 자동으로 목숨모드 OFF
            life.setChecked(false);
        } else {
            editor.putBoolean("inifMode", false);
        }
        editor.commit();
    }
}