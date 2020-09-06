package me.hugo.quiz.task;

import me.hugo.quiz.QuizPlugin;
import me.hugo.quiz.managers.QuizManager;
import me.hugo.quiz.utils.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class QuizExpireTask {

    private QuizManager quizManager;
    private BukkitTask quizTask;

    public QuizExpireTask(QuizManager quizManager) {
        this.quizManager = quizManager;

        long time = ConfigManager.getIntConfig("time") * 20;

        expiresIn(time);
    }

    private void expiresIn(long time) {
        quizTask = Bukkit.getScheduler().runTaskTimerAsynchronously(QuizPlugin.getInstance(), new Runnable() {

            @Override
            public void run() {
                finishEvent();
            }

        }, time, 20L);
    }

    private void finishEvent() {
        quizManager.finish();
        Bukkit.getScheduler().cancelTask(quizTask.getTaskId());
    }

}
