package org.github.sxntido.commands.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.sxntido.BACore;
import org.github.sxntido.tools.Utils;

public class TpaAccept implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return true;
        Player p = (Player)s;
        if (args.length == 0) {
            Player t = BACore.tpa.get(p);
            t.teleport(p.getLocation());
            //p.sendMessage("successful!");
            Utils.sendMessage(p, BACore.SetMessage().getString("commands.tpa.successful"), true);
            //t.sendMessage("successful!");
            Utils.sendMessage(t, BACore.SetMessage().getString("commands.tpa.successful"), true);
        }
        return false;
    }
}