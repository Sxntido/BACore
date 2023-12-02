package org.github.sxntido.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.sxntido.BACore;
import org.github.sxntido.tools.Utils;

public class Action implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ("&eBACore &8» &cError this command cannot be executed in console")));
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lBACore &fversión 1.0 desarrollado por @Sxntido."));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fEste complemento fue patrocinado por Aquatic Studios"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
            return true;

        }

        if (args[0].equalsIgnoreCase("help")) {
            if (p.hasPermission("bacore.help") || p.hasPermission("bacore.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/bacore reload &7- &fRecarga todo los archivos"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
                return true;
            }
            Utils.sendMessage(p, BACore.SetMessage().getString("admin.no-permission"), true);
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (p.hasPermission("bacore.reload") || p.hasPermission("bacore.admin")) {
                BACore.SetConfig().Reload();
                BACore.SetMessage().Reload();
                Utils.sendMessage(p, BACore.SetMessage().getString("admin.reload"), true);
                return true;
            }
            Utils.sendMessage(p, BACore.SetMessage().getString("admin.no-permission"), true);
        }
        return false;
    }
}