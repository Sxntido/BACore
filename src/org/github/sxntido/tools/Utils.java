package org.github.sxntido.tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.github.sxntido.BACore;

public class Utils {

    public static String Color(String Text){
        return ChatColor.translateAlternateColorCodes('&', Text);
    }

    public static void sendMessage(Player p, String s, boolean prefix) {
        if(prefix) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', BACore.SetConfig().getString("modules.prefix")+s));
        } else if(!prefix) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',s));
        }
    }

    public static void getLoggs(String s, boolean prefix) {
        if (prefix) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', BACore.SetConfig().getString("modules.prefix")+s));
        } else if (!prefix) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    public static String SoundAPI(Player p, String config) {
        String path = config;
        String[] separados = path.split(":");
        try {
            Sound sound = Sound.valueOf(separados[0]);
            int volumen = Integer.valueOf(separados[1]);
            float pitch = Float.valueOf(separados[2]);
            p.playSound(p.getLocation(), sound, volumen, pitch);
        } catch (IllegalArgumentException e) {
            getLoggs("&fError the sound: &b" + separados + " , &fis invalid", true);
        } catch (ArrayIndexOutOfBoundsException ex) {
            getLoggs("&fError the sound: &b" + separados + " , &fis invalid", true);
        }
        return path;
    }

}
