package me.hugo.quiz.objects;

import java.util.List;

public class Quiz {

    private String question;
    private List<Answer> answers;
    private int reward;

    public Quiz(String question, List<Answer> answers, int reward) {
        this.question = question;
        this.answers = answers;
        this.reward = reward;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getReward() {
        return reward;
    }
}
