package me.hugo.quiz.task;

import me.hugo.quiz.QuizPlugin;
import me.hugo.quiz.managers.QuizManager;
import me.hugo.quiz.utils.config.ConfigManager;
import org.bukkit.Bukkit;

public class QuizExpireTask {

    private QuizManager quizManager;

    public QuizExpireTask(QuizManager quizManager) {
        this.quizManager = quizManager;

        long time = ConfigManager.getIntConfig("time") * 20;

        expiresIn(time);
    }

    private void expiresIn(long time) {
        Bukkit.getScheduler().runTaskLater(QuizPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                quizManager.finish();
            }
        }, time);
    }

}
