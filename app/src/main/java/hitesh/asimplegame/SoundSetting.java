package hitesh.asimplegame;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
/*처음에 볼륨키를 넣었는데 굳이 필요 없을 것 같아서 그냥 지우고 ON/OFF만 넣었습니다*/
/*효과음 음원은 바꿔야합니다*/
/*MediaPlayer는 배경음악 처리 soundPool은 효과음 처리입니다*/

public class SoundSetting extends Activity {
    private static MediaPlayer mp;
    Switch sound = findViewById(R.id.swt_sound);
    Switch effect = findViewById(R.id.swt_effect);
    int soundPlay;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mp = MediaPlayer.create(this,R.raw.forget);
        mp.setLooping(true);

        //효과음 %버전에 따라 다르게 빌드해 줘야한다%
        setSoundPool(); //버전에 따라 다르게 빌드 해준다.
        soundPlay = soundPool.load(this,R.raw.forget,1); //음원 바꿀 것
        soundPool.play(soundPlay,1f,1f,0,0,1f);

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //배경음악
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked){
                        mp.start();
                    } else{
                        mp.stop();
                        mp.reset();
                    }
            }
        });

        effect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // 효과음 버튼 Listenr마다 넣어주기
            @Override //볼륨조절로 온오프하기
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    soundPool.setVolume(soundPlay,0,0); //min
                } else{
                    soundPool.setVolume(soundPlay,1,1);//max
                }
            }
        });
    }

    private void setSoundPool() { //생성자 soundPool생성자가 최근에 deprecate되었기 때문에
        if (soundPool == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(8).build();
            } else {
                // maxStream, streamType, quality
                soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
            }
        }
    }
}
