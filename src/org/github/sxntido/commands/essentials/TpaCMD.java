package org.github.sxntido.commands.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.sxntido.BACore;
import org.github.sxntido.tools.Utils;

public class TpaCMD implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player))
            return true;
        Player p = (Player)s;
        try {
            Player t = Bukkit.getPlayer(args[0]);
            BACore.tpa.put(t, p);
            //t.sendMessage("a" + p.getName() + "send you tpa request");
            //p.sendMessage("Tpa Request send!");
            Utils.sendMessage(t, BACore.SetMessage().getString("commands.tpa.request").replace("%bacore_player%", p.getName()), true);
            Utils.sendMessage(p, BACore.SetMessage().getString("commands.tpa.send"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
