package me.hugo.quiz.objects;

import me.hugo.quiz.managers.QuizManager;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QuizEvent {

    private Quiz currentQuiz;
    private Set<String> playersWhoAnswered;

    private QuizManager quizManager;

    public QuizEvent(Quiz currentQuiz, QuizManager quizManager) {
        this.currentQuiz = currentQuiz;
        this.quizManager = quizManager;
        this.playersWhoAnswered = new HashSet<>();
    }

    public boolean contains (Player player) {
        return playersWhoAnswered.contains(player.getName());
    }

    public void addPlayer(Player player) {
        playersWhoAnswered.add(player.getName());
    }

    public Quiz getQuiz() {
        return currentQuiz;
    }
}
