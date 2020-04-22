package hitesh.asimplegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuizDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "QuizDBOpenHelper";

    //private static int DATABASE_RANDOM = 1;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    private static final String TABLE_VOICE = "voicequest";

    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTA = "opta";
    private static final String KEY_OPTB = "optb";
    private static final String KEY_OPTC = "optc";
    private static int size = 5;

    private SQLiteDatabase database;

    private int level;

    public QuizDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void setSize(int i) {
        size = i;
    }

    public static int getSize() {
        return size;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {       //디비를 쓰긴함?
        database = db;

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT)";

        String voice_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_VOICE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT )";

        db.execSQL(sql);
        db.execSQL(voice_sql);
        addVoiceQuestion();
//        addQuestion(levelActivity.getLevel());
        addQuestion();
//        db.close();
    }

    private void addQuestion() {
        for (int i = 0; i < getSize(); i++) {
            int questNumA = (int) (Math.random() * 10);
            int questNumB = (int) (Math.random() * 10);
            int questAnswer = 0;
            int questOption = (int) (Math.random() * getLevel());

            int optA = 0, optB = 0, optC = 0;
            int optSelRandom = 0;

            String questionString;
            Question question;

            switch (questOption) {
                case 0: // +
                    questAnswer = questNumA + questNumB;
                    optSelRandom = (int) (Math.random() * 3);

                    questionString = (String.valueOf(questNumA) + " + " + String.valueOf(questNumB) + " = ?");

                    switch (optSelRandom) {
                        case 0:
                            optA = questNumA + questNumB;
                            optB = (int) (Math.random() * 10);
                            optC = (int) (Math.random() * 10);
                            break;
                        case 1:
                            optA = (int) (Math.random() * 10);
                            optB = questNumA + questNumB;
                            optC = (int) (Math.random() * 10);
                            break;
                        case 2:
                            optA = (int) (Math.random() * 10);
                            optB = (int) (Math.random() * 10);
                            optC = questNumA + questNumB;
                            break;
                    }

                    question = new Question(questionString, String.valueOf(optA), String.valueOf(optB), String.valueOf(optC), String.valueOf(questAnswer));
                    addQuestion(question);
                    break;

                case 1: // -
                    questAnswer = questNumA - questNumB;
                    optSelRandom = (int) (Math.random() * 2);

                    questionString = (String.valueOf(questNumA) + " - " + String.valueOf(questNumB) + " = ?");

                    switch (optSelRandom) {
                        case 0:
                            optA = questNumA - questNumB;
                            optB = (int) (Math.random() * 10);
                            optC = (int) (Math.random() * 10);
                            break;
                        case 1:
                            optA = (int) (Math.random() * 10);
                            optB = questNumA - questNumB;
                            optC = (int) (Math.random() * 10);
                            break;
                        case 2:
                            optA = (int) (Math.random() * 10);
                            optB = (int) (Math.random() * 10);
                            optC = questNumA - questNumB;
                            break;
                    }

                    question = new Question(questionString, String.valueOf(optA), String.valueOf(optB), String.valueOf(optC), String.valueOf(questAnswer));
                    addQuestion(question);
                    break;
                case 2: // *
                    questAnswer = questNumA * questNumB;
                    optSelRandom = (int) (Math.random() * 2);

                    questionString = (String.valueOf(questNumA) + " * " + String.valueOf(questNumB) + " = ?");

                    switch (optSelRandom) {
                        case 0:
                            optA = questNumA * questNumB;
                            optB = (int) (Math.random() * 10);
                            optC = (int) (Math.random() * 10);
                            break;
                        case 1:
                            optA = (int) (Math.random() * 10);
                            optB = questNumA * questNumB;
                            optC = (int) (Math.random() * 10);
                            break;
                        case 2:
                            optA = (int) (Math.random() * 10);
                            optB = (int) (Math.random() * 10);
                            optC = questNumA * questNumB;
                            break;
                        default:
                            break;
                    }

                    question = new Question(questionString, String.valueOf(optA), String.valueOf(optB), String.valueOf(optC), String.valueOf(questAnswer));
                    addQuestion(question);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {      //이건 사용을 안하는데?
        setLevel(0);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);      //테이블 생성을 위한 문자열 전달
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question quest) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();     //ContentResolver가 처리 할 수 있는 값 집합을 저장
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());

        // Inserting Row
        database.insert(TABLE_QUEST, null, values);
    }

    //<===========VOICE 문제 관련 DB처리 (SQLITE)======================
    //생성자
    private void addVoiceQuestion() {
        VoiceQuestion v1 = new VoiceQuestion("4 더하기 5 빼기 3은 무엇일까요?", "6");
        VoiceQuestion v2 = new VoiceQuestion("3 곱하기 7 더하기 3은 무엇일까요?", "24");
        VoiceQuestion v3 = new VoiceQuestion("15 빼기 4 곱하기 3은 무엇일까요?", "3");
        VoiceQuestion v4 = new VoiceQuestion("4 나누기 2 더하기 5은 무엇일까요?", "7");
        VoiceQuestion v5 = new VoiceQuestion("5더하기 5은 무엇일까요?", "10");
        addVoiceQuestion(v1);
        addVoiceQuestion(v2);
        addVoiceQuestion(v3);
        addVoiceQuestion(v4);
        addVoiceQuestion(v5);
    }

    //DB 적용
    public void addVoiceQuestion(VoiceQuestion quest) {
        ContentValues values = new ContentValues();     //ContentResolver가 처리 할 수 있는 값 집합을 저장
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        // Inserting Row
        database.insert(TABLE_VOICE, null, values);
    }

    //voice 문제 반환 (sqlite)
    public List<VoiceQuestion> getAllVoiceQuestions() {
        List<VoiceQuestion> quesList = new ArrayList<VoiceQuestion>();
        String selectQuery = "SELECT  * FROM " + TABLE_VOICE;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                VoiceQuestion question = new VoiceQuestion();
                question.setID(cursor.getInt(0));
                question.setQUESTION(cursor.getString(1));
                question.setANSWER(cursor.getString(2));

                quesList.add(question);
            } while (cursor.moveToNext());
        }
        checkFile();
        return quesList;
    }
//=========================================================>

    public int getLevel() {
        return level;
    }

    public void setLevel(int lv) {
        level = lv;
    }


    public List<Question> getAllQuestions(int lv) {
//        getLevel();
        setLevel(lv);
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        database = this.getReadableDatabase();      //이때 onCreate
        Cursor cursor = database.rawQuery(selectQuery, null);       //rawQuery : 데이터베이스에 저장된 데이터를 찾아서 가져옴       //커서는 행단위 참조
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {     //moveToFirst : 가장 첫번째 행 선택
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));      //선택된 행의 첫번째 열
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));

                quesList.add(quest);
            } while (cursor.moveToNext());      //moveToNext : 순서상으로 다음 행 선택
        }
        checkFile();
        setLevel(0);

        return quesList;
    }

    public void checkFile() {
        String databasePath = "/data/user/0/hitesh.asimplegame/databases";
        File mFile = new File(databasePath);
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] files = mFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].delete()) {
                        Log.d(TAG, "==== Foldering File Deleted.");
                    } else {
                        Log.d(TAG, "==== Foldering File Not Deleted.");
                    }
                }
            }

            if (mFile.delete()) {
                Log.d(TAG, "==== File Deleted.");
            } else {
                Log.d(TAG, "==== File Deleted.");
            }
        }
    }
}