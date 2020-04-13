package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {
    private User CurUser;
    private Button SignUp;
    private EditText NAME;
    private EditText ID;
    private EditText PW;
    private String id;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final UserDBOpenHelper db = new UserDBOpenHelper(this);

        SignUp = (Button) findViewById(R.id.submit);
        NAME = (EditText)findViewById(R.id.name_form);
        ID= (EditText)findViewById(R.id.id_form);
        PW= (EditText)findViewById(R.id.password_form);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = ID.getText().toString(); //string으로 변환하여 저장
                password = PW.getText().toString();
                name = NAME.getText().toString();

                if(id.isEmpty()||password.isEmpty()||name.isEmpty()){ //만약 빈칸이라면
                    Toast myToast = Toast.makeText(getApplicationContext(), R.string.NULL_MESSAGE,Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    CurUser =new User(id,password,name);
                    db.addUser(CurUser);
                    Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);//다음페이지
                    startActivity(intent);
                }
            }
        });
    }
}