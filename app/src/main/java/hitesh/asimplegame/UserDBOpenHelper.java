package hitesh.asimplegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.sql.DriverManager.println;

public class UserDBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USER";
    //테이블 이름
    private static final String TABLE_USER = "user";
    private static final String TABLE_SCORE = "score"; //스코어 테이블을 따로 만들어 조인해주기
    private static final String NAME = "name"; // user name
    private static final String ID = "id"; // user id
    private static final String PASSWORD = "password"; // user password
    private static final String SCORE = "score";

    private SQLiteDatabase database;

    public UserDBOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {//TABLE_USER(ID(PRI),PW,NAME); TABLE_SCORE(ID(PRI),SCORE)
        database = db;
        String sql_user = "CREATE TABLE IF NOT EXISTS " + TABLE_USER+ " ( "
                + ID + " TEXT PRIMARY KEY, " + NAME + " TEXT, "
                + PASSWORD + " TEXT)";

        String sql_score = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE+ " ( "
                + ID + " TEXT PRIMARY KEY, " + SCORE + " INT)";
        db.execSQL(sql_user);
        db.execSQL(sql_score);
        // db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        // Create tables again
        onCreate(db);
    }
    public void insertUser(User user){
        ContentValues values = new ContentValues();
        values.put(ID,user.getID());
        values.put(NAME,user.getNAME());
        values.put(PASSWORD,user.getPW());

        database.insert(TABLE_USER, null,values);
    }
    public void addScore(User user, int score){
        ContentValues values = new ContentValues();
        values.put(SCORE,score);
        values.put(ID,user.getID());
        database.insert(TABLE_SCORE, null,values);
    }
    public User getUser(String ID){//ID에 해당되는 유저의 스코어를 포함한 모든 정보를 가져온다
        User user = new User(); //중복아이디 막기... 이거 너무 귀찮으니까 나중에 하자

        String selectQuery ="SELECT * FROM user INNER JOIN score ON user.ID=score.ID WHERE user.ID=ID";
        //이너 조인, 일단 SQL
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            user.setID(cursor.getString(0));
            user.setNAME(cursor.getString(1));
            user.setPW(cursor.getString(2));
            println(cursor.getString(0));
            do {
                user.ScoreUP(cursor.getInt(3));//score만 지속적으로 가져오기
            } while (cursor.moveToNext());
        }
        cursor.close();
        return user;
    }
    //DB열고 닫기 메모리 누수 방지 close 하지 않아도 된다는

    public static String getTableUser() {
        return TABLE_USER;
    }
    public String getTableScore(){
        return TABLE_SCORE;
    }
}