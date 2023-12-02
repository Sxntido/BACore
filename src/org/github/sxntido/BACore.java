package org.github.sxntido;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.sxntido.commands.Action;
import org.github.sxntido.commands.NightCMD;
import org.github.sxntido.commands.essentials.TpaAccept;
import org.github.sxntido.commands.essentials.TpaCMD;
import org.github.sxntido.files.Config;
import org.github.sxntido.listeners.Mention;

import java.util.HashMap;

public class BACore extends JavaPlugin {

    public static BACore instance;

    public static BACore getInstance() {
        return instance;
    }

    private static Config config;

    public static Config SetConfig() {
        return config;
    }

    private static Config messages;

    public static Config SetMessage() {
        return messages;
    }

    public static HashMap<Player, Player> tpa = new HashMap<>();

    public void onEnable() {
        instance = this;
        config = new Config("config");
        messages = new Config("messages");

        RegisterCommands();
        RegisterListener();

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&8")));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&eBACore &8» &fPowered by &bAquatic Studios")));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&8")));

    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&8")));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&eBACore &8» &fPowered by &bAquatic Studios")));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&8")));
    }

    public void RegisterListener() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents((Listener) new Mention(), (Plugin) this);
    }

    public void RegisterCommands() {
        getCommand("vision").setExecutor(new NightCMD());
        getCommand("bacore").setExecutor(new Action());

        getCommand("btpa").setExecutor(new TpaCMD());
        getCommand("btpaccept").setExecutor(new TpaAccept());
    }

}
