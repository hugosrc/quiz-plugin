package me.hugo.quiz.menus;

import me.hugo.quiz.QuizPlugin;
import me.hugo.quiz.managers.QuizManager;
import me.hugo.quiz.objects.Answer;
import me.hugo.quiz.objects.Quiz;
import me.hugo.quiz.utils.builder.ItemBuilder;
import me.hugo.quiz.utils.heads.CustomSkull;
import me.hugo.quiz.utils.inventory.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuizMenu extends Menu {

    private Quiz quiz;
    private QuizManager quizManager;

    public QuizMenu(String title, int rows, Quiz quiz) {
        super(title, rows);
        this.quiz = quiz;
        quizManager = QuizPlugin.getInstance().getQuizManager();
    }

    @Override
    public void open(Player player) {

        setItem(
                10,
                new ItemBuilder(Material.BOOK_AND_QUILL)
                        .setDisplayName("§aResponda a Pergunta")
                        .lore(getCuttedLore(quiz.getQuestion()))
                        .build(),
                event -> {});

        int slot = 28;

        for (Answer answer : quiz.getAnswers()) {
            int i = quiz.getAnswers().indexOf(answer);

            ItemStack answerItem = new ItemBuilder(CustomSkull.getHead("question"))
                    .setDisplayName("§aResposta #" + (i + 1))
                    .lore(getCuttedLore(answer.getAnswer()))
                    .addLoreLine(" ")
                    .addLoreLine("§7Clique para responder")
                    .build();

            setItem(slot, answerItem, event -> quizManager.answer(player, answer));
            slot += 2;
        }

        super.open(player);
    }

    public List<String> getCuttedLore(String text) {
        String parsed = text.replaceAll("(.{25})", "$1%s");

        return Arrays.stream(parsed.split("%s")).map(line -> "§f" + line).collect(Collectors.toList());
    }
}
