package me.hugo.quiz.commands;

import me.hugo.quiz.managers.QuizManager;
import me.hugo.quiz.menus.QuizMenu;
import me.hugo.quiz.objects.QuizEvent;
import me.hugo.quiz.utils.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuizCommand implements CommandExecutor {

    private QuizManager quizManager;

    public QuizCommand(QuizManager quizManager) {
        this.quizManager = quizManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(ConfigManager.getMessageConfig("messages.command_quiz_answer"));

            if (sender.hasPermission("staff.servername.quiz")) {
                sender.sendMessage(ConfigManager.getMessageConfig("messages.command_quiz_start"));
            }

            return false;
        }

        if (args[0].equalsIgnoreCase("responder")) {
            if (!(sender instanceof Player)) return false;

            Player player = (Player) sender;

            QuizEvent currentQuiz = quizManager.getCurrentQuizEvent();

            if (currentQuiz == null) {
                player.sendMessage(ConfigManager.getMessageConfig("messages.event_not_happening"));
                return false;
            }

            if (currentQuiz.contains(player)) {
                player.sendMessage(ConfigManager.getMessageConfig("messages.already_answered"));
                return false;
            }

            new QuizMenu("Responda o Quiz", 5, currentQuiz.getQuiz()).open(player);

            return true;
        }

        if (args[0].equalsIgnoreCase("iniciar")) {
            if (!sender.hasPermission("staff.servername.quiz")) {
                sender.sendMessage(ConfigManager.getMessageConfig("messages.has_no_permission"));
                return false;
            }

            quizManager.start(sender);
            return true;
        }

        return false;
    }

}
