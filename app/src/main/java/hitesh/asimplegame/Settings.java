package hitesh.asimplegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

/*처음에 볼륨키를 넣었는데 굳이 필요 없을 것 같아서 그냥 지우고 ON/OFF만 넣었습니다*/
/*MediaPlayer는 배경음악 처리 soundPool은 효과음 처리입니다*/

public class Settings extends Activity {
    private static MediaPlayer mp;

    private boolean isBgmChecked,isEffectChecked, isLifeChecked;

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    Switch life, effect, bgm; //모드 추가하려면 더 해도..

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        life = findViewById(R.id.btn_life);
        bgm = findViewById(R.id.btn_bgm);
        effect = findViewById(R.id.btn_effect);
        //===============상태저장=================//
        sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        //===============상태 반영================//
        if(sharedPref.getInt("lifeMode",1)==3) life.setChecked(true); //초기 설정 값과 다르다면
        if(sharedPref.getInt("effect",0)==0) effect.setChecked(false);
        bgm.setChecked(sharedPref.getBoolean("bgm",false));
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
            editor.putInt("lifeMode", 3);
        } else {
            editor.putInt("lifeMode", 1);
        }
        editor.commit();
    }
}