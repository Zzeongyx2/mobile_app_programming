package hitesh.asimplegame;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*QuizDBOpenHelper에 쓰려면 따로 SQLiteDatabase 객체를 빼서 직접 사용 아니면 충돌일어남*/
//import static hitesh.asimplegame.QuizDBOpenHelper.setDatabaseRandoming;

public class ResultActivity extends Activity {
    //private DatabaseReference mDatabase;
    QuizDBOpenHelper helper = new QuizDBOpenHelper(this);
    SQLiteDatabase db;
    SharedPreferences sf;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = helper.getWritableDatabase();//writable db
        sf=getSharedPreferences("USER", MODE_PRIVATE);//설정 값

        TextView textResult = findViewById(R.id.textResult);
        Bundle b = getIntent().getExtras();		//getExtras() : 다른 activity에 데이터 전달

        int score = b.getInt("score");
        textResult.setText("Your score is " + " " + score + ". Thanks for playing my game.");

        username = sf.getString("id","anonymous");//로그인 유저가 아닐시 익명으로 저장

        ContentValues values = new ContentValues();     //ContentResolver가 처리 할 수 있는 값 집합을 저장
        values.put(helper.getKeyId(),username);
        values.put(helper.getKeyScore(), score);
        db.insert(helper.getTableScore(),null,values); // 직접삽입


        //Log.d("ResultActivity",username);
        //Toast.makeText(getApplicationContext(), "inifMode : " + sf.getString("id",username), Toast.LENGTH_SHORT).show();

    }

    public void playagain(View o) {
        helper.setDBRandom();

        Intent intent = new Intent(this, SelectQuestion.class);
        startActivity(intent);
    }
}