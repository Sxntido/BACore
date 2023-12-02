package org.github.sxntido.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.github.sxntido.BACore;
import org.github.sxntido.tools.Utils;

public class NightCMD implements CommandExecutor {
    int counter = 0;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("vision")) {
                this.counter++;
                if (this.counter == 1) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000000, 1));
                    Utils.sendMessage(p, BACore.SetMessage().getString("commands.night-vision.enabled"), true);
                }
                if (this.counter == 2) {
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    Utils.sendMessage(p, BACore.SetMessage().getString("commands.night-vision.disabled"), true);
                    this.counter = 0;
                }
                return false;
            }
            Utils.getLoggs("&cThis command is not available in the console", true);
            return false;
        }
        return false;
    }
}