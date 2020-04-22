package hitesh.asimplegame;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;


public class SelectQuestion extends Activity {
    Button btnLogOut;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    //효과음
    private SoundPool soundPool;
    private int soundID;
    private int vol;//볼륨, 추후 수정 이 페이지는 빠른 업데이트를 위해 vol을 쓰지 않는다
    SharedPreferences sf;

    Bundle b = new Bundle();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        //setting
        sf = getSharedPreferences("settings", MODE_PRIVATE);
        vol = sf.getInt("effect", 1);//초기
        Toast.makeText(getApplicationContext(), "vol : " + vol, Toast.LENGTH_SHORT).show();
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.button, 1);
        btnLogOut = (Button) findViewById(R.id.btn_logout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(SelectQuestion.this, LoginActivity.class);
                startActivity(I);

            }
        });
    }

    public void math(View o) {
        soundPool.play(soundID, sf.getInt("effect", 1), sf.getInt("effect", 1), 0, 0, 0);//빠른 업데이트를 위해....
        Intent intent = new Intent(this, LevelActivity.class);
        b.putInt("mode", 1);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void infinity(View o) {
        soundPool.play(soundID, sf.getInt("effect", 1), sf.getInt("effect", 1), 0, 0, 0);//빠른 업데이트를 위해....
        Intent intent = new Intent(this, LevelActivity.class);
        b.putInt("mode", 2);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void setting(View o) {
        soundPool.play(soundID, sf.getInt("effect", 1), sf.getInt("effect", 1), 0, 0, 0);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}