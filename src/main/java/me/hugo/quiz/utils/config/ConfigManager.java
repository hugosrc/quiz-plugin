package me.hugo.quiz.utils.config;

import me.hugo.quiz.QuizPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config = QuizPlugin.getInstance().getConfig();

    public static String getMessageConfig(String path) {
        return config.getString(path).replace("&", "§");
    }

    public static int getIntConfig(String path) {
        return config.getInt(path);
    }

}
