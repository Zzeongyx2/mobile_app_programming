package hitesh.asimplegame;

import android.graphics.drawable.Drawable;

public class Score { //혹시나 하는 확장을 위해 클래스 생성
    private int score;
    private String user;
    private int order;//그냥 넣은 거 빼도 됨

    public Score() {
        score = 0;
        user = " ";
    }

    public Score(String user, int score) {
        this.score = score;
        this.user = user;
    }

    public void setOrder(int i) {
        order = i;
    }

    public int getOrder() {
        return this.order;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getUser() {
        return user;
    }
}
