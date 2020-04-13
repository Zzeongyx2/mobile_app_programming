package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private UserDBOpenHelper helper;
    private SQLiteDatabase database;
    Button SIGNUP;
    Button LOGIN;
    EditText ID;
    EditText PW;
    String sql;
    Cursor cursor;

//DB값을 String으로 변환해서 담는 임시 변수들
    private String id;
    private String pw;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SIGNUP = (Button)findViewById(R.id.signup);
        LOGIN = (Button)findViewById(R.id.login);
        ID= (EditText)findViewById(R.id.username);
        PW= (EditText)findViewById(R.id.password);

        SIGNUP.setEnabled(true);
        LOGIN.setEnabled(true);
        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SignUpActivity.class);//다음페이지
                startActivity(intent);
            }
        });

        LOGIN.setOnClickListener(new View.OnClickListener() {//로그인 버튼 클릭시
            @Override
            public void onClick(View view) {
                id = ID.getText().toString();
                pw = PW.getText().toString();

                if(id.isEmpty()||pw.isEmpty()){//비어있다면
                    Toast myToast = Toast.makeText(getApplicationContext(), R.string.NULL_MESSAGE,Toast.LENGTH_SHORT);//비어있다는 메세지
                    myToast.show();
                    return;
                }
                sql ="SELECT name FROM"+helper.getTableUser()+"WHERE id = "+id+" AND password = "+pw;//sql 작성
                if(cursor.getCount()!=1){//검색 된 결과가 없을 시
                    Toast myToast = Toast.makeText(getApplicationContext(), R.string.NOT_MATCH_MESSAGE,Toast.LENGTH_SHORT); //없다는 메세지
                    myToast.show();
                    return;
                }else{//검색된 메세지가 있을 시
                    Toast myToast = Toast.makeText(getApplicationContext(), R.string.SUCCESS_MESSAGE,Toast.LENGTH_SHORT);
                    myToast.show();

                    Intent intent = new Intent(getApplicationContext(),QuestionActivity.class);//다음 인텐트 시작
                    startActivity(intent);
                }
            }
        });
    }
}
