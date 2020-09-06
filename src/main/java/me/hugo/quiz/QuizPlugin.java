package me.hugo.quiz;

import me.hugo.quiz.commands.QuizCommand;
import me.hugo.quiz.managers.QuizManager;
import me.hugo.quiz.utils.inventory.MenuListener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

public final class QuizPlugin extends JavaPlugin {

    private static QuizPlugin instance;
    private QuizManager quizManager;
    private Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();

        if (!setupEconomy()) {
            System.out.println("The plugin was disabled because the Vault was not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        quizManager = new QuizManager();
        quizManager.loadQuizzes();

        registerEvents();
        registerCommands();
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    private void registerCommands() {
        getCommand("quiz").setExecutor(new QuizCommand(quizManager));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);

        if (provider == null) {
            return false;
        }

        economy = provider.getProvider();
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }

    public static QuizPlugin getInstance() {
        return instance;
    }
}
