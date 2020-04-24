package hitesh.asimplegame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class RankingActivity extends Activity {
    String message = "";
    //TextView board;
    QuizDBOpenHelper helper = new QuizDBOpenHelper(this);

    private List<Score> scoreList;
    private int size;

    ListView listview;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        adapter = new ListViewAdapter();
        //리스트뷰 참조 및 adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        scoreList = helper.getAllScore();
        //사이즈 조정
        size = scoreList.size();
        if (size > 10) size = 10;

        for (int i = 0; i < size; i++) {
            Score score = scoreList.get(i);
            adapter.addItem(i+1,score.getUser(), score.getScore());//순위, 유저이름, 스코어
        }
        //board.setText(message);
    }
}
