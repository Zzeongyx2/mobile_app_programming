package hitesh.asimplegame;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

//나중에 유저 정보 더 추가
public class User extends Activity {
    private String ID;
    private String PW;
    private String NAME;
    private List<Integer> SCORE;

    public User(){
        ID = " ";
        PW = " ";
        NAME =" ";
        SCORE = new ArrayList<Integer>(0);
    }

    public User(String id, String password, String name) {
        NAME = name;
        PW = password;
        ID = id;
    }

    public void ScoreUP(int new_score){
        SCORE.add(new_score);
    }
    public String getID() { return ID; }
    public String getPW() {
        return PW;
    }
    public String getNAME() {
        return NAME;
    }
    public void setID(String id) {
        NAME = id;
    }
    public void setPW(String password) {
        PW = password;
    }
    public void setNAME(String name) {
        NAME = name;
    }

}
