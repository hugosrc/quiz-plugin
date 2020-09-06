package me.hugo.quiz.managers;

import me.hugo.quiz.QuizPlugin;
import me.hugo.quiz.objects.Answer;
import me.hugo.quiz.objects.Quiz;
import me.hugo.quiz.objects.QuizEvent;
import me.hugo.quiz.task.QuizExpireTask;
import me.hugo.quiz.utils.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class QuizManager {

    private Map<UUID, Quiz> quizzes = new HashMap<>();;
    private Random random;

    private QuizEvent currentQuizEvent;

    public QuizManager() {
        random = new Random();
    }

    public void loadQuizzes() {
        FileConfiguration config = QuizPlugin.getInstance().getConfig();

        for (String quiz : config.getConfigurationSection("quizzes").getKeys(false)) {
            List<String> answers = config.getStringList("quizzes." + quiz + ".answers");
            List<Answer> quizAnswers = new ArrayList<>();

            int correct = config.getInt("quizzes." + quiz + ".correct");

            for (String answer : answers) {
                quizAnswers.add(new Answer(answer, answers.get(correct - 1).equals(answer)));
            }

            createQuiz(
                    new Quiz(
                            config.getString("quizzes." + quiz + ".question"),
                            quizAnswers,
                            config.getInt("quizzes." + quiz + ".reward")));
        }

        System.out.println(quizzes.size() + " quizzes were loaded!");
    }

    public void createQuiz(Quiz quiz) {
        quizzes.put(UUID.randomUUID(), quiz);
    }

    public Quiz get(UUID uuid) {
        return quizzes.get(uuid);
    }

    public QuizEvent getCurrentQuizEvent() {
        return currentQuizEvent;
    }

    public void start(CommandSender sender) {
        if (currentQuizEvent != null) {
            sender.sendMessage(ConfigManager.getMessageConfig("messages.event_going_on"));
            return;
        }

        List<UUID> collect = new ArrayList<>(quizzes.keySet());
        UUID uuid = collect.get(random.nextInt(quizzes.size()));

        currentQuizEvent = new QuizEvent(uuid, this);
        new QuizExpireTask(this);

        Bukkit.broadcastMessage(ConfigManager.getMessageConfig("messages.event_started"));
    }

    public void finish() {
        currentQuizEvent = null;
        Bukkit.broadcastMessage(ConfigManager.getMessageConfig("messages.event_ended"));
    }

    public void answer(Player player, Answer answer) {
        player.closeInventory();

        if (currentQuizEvent == null) return;

        Quiz quiz = currentQuizEvent.getQuiz();
        currentQuizEvent.addPlayer(player);

        if (!answer.isCorrect()) {
            player.sendMessage(ConfigManager.getMessageConfig("messages.wrong_answer"));
            return;
        }

        QuizPlugin.getInstance().getEconomy().depositPlayer(player, quiz.getReward());

        player.sendMessage(ConfigManager.getMessageConfig("messages.right_answer") + quiz.getReward() + " coins!");
    }

}
