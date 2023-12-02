package org.github.sxntido.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.github.sxntido.BACore;
import org.github.sxntido.api.ActionBarAPI;
import org.github.sxntido.api.CustomTitle;
import org.github.sxntido.tools.Utils;
import org.github.sxntido.xseries.XSound;

import java.util.Collection;
import java.util.Iterator;

public class Mention implements Listener {

    @EventHandler
    public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent event) {
        String token = event.getLastToken();
        if (token.startsWith("@")) {
            Collection autoCompletions = event.getTabCompletions();
            autoCompletions.clear();
            String begin = token.replaceAll("@", "").toLowerCase();
            Iterator var6 = Bukkit.getOnlinePlayers().iterator();

            while (var6.hasNext()) {
                Player player = (Player) var6.next();
                String format = BACore.SetConfig().getString("mention.format.everyone");
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(begin)) {
                    autoCompletions.add("@" + playerName);
                } else if (format.toLowerCase().startsWith(begin)) {
                    autoCompletions.add("@" + format);
                }
            }
        }

    }

    @EventHandler
    public void callPlayer(AsyncPlayerChatEvent e) {
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while (true) {
            Player player;
            String coloredText;
            String coloredTextSub;
            String message;
            String playerName;
            do {
                do {
                    if (!var3.hasNext()) {
                        return;
                    }

                    player = (Player) var3.next();
                    String title = BACore.SetConfig().getString("mention.messages.title").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", player.getName()).replace("%message%", e.getMessage());
                    String subtitle = BACore.SetConfig().getString("mention.messages.subtitle").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", player.getName()).replace("%message%", e.getMessage());
                    coloredText = ChatColor.translateAlternateColorCodes('&', title);
                    coloredTextSub = ChatColor.translateAlternateColorCodes('&', subtitle);
                    message = e.getMessage();
                    playerName = "@" + player.getName();
                } while (!e.getMessage().contains(playerName));
            } while (!e.getPlayer().hasPermission("surfing.mention.use"));

            message = message.replaceFirst("@(?i)" + player.getName(), BACore.SetConfig().getString("mention.options.player")).replaceAll("@%player%", "@" + player.getName());
            e.setMessage(ChatColor.translateAlternateColorCodes('&', message));
            if (BACore.SetConfig().getBoolean("mention.config.sound.switch")){
                //Utils.SoundAPI(player, BACore.SetConfig().getString("mention.config.sound.set"));
                XSound.play(player, BACore.SetConfig().getString("mention.config.sound.set"));
            }

            if (BACore.SetConfig().getBoolean("mention.config.titles")) {
                CustomTitle.sendTitle(player, BACore.SetConfig().getInt("mention.options.title.fadein") * 20, BACore.SetConfig().getInt("mention.options.title.fadein") * 20, BACore.SetConfig().getInt("options.title.fadeout") * 20, coloredText, coloredTextSub);
            }

            if (BACore.SetConfig().getBoolean("mention.config.actionbar")) {
                ActionBarAPI bar = new ActionBarAPI();
                bar.setMessage(ChatColor.translateAlternateColorCodes('&', BACore.SetConfig().getString("mention.messages.actionbar").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", player.getName()).replace("%message%", e.getMessage())));
                bar.send(new Player[]{player});
            }
        }
    }

    @EventHandler
    public void callEveryone(AsyncPlayerChatEvent e) {
        Iterator var3 = Bukkit.getServer().getOnlinePlayers().iterator();

        while (true) {
            Player everyone;
            String coloredText;
            String coloredTextSub;
            String format;
            String allmsg;
            do {
                do {
                    if (!var3.hasNext()) {
                        return;
                    }

                    everyone = (Player) var3.next();
                    String title = BACore.SetConfig().getString("mention.messages.title").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", everyone.getName()).replace("%message%", e.getMessage());
                    String subtitle = BACore.SetConfig().getString("mention.messages.subtitle").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", everyone.getName()).replace("%message%", e.getMessage());
                    coloredText = ChatColor.translateAlternateColorCodes('&', title);
                    coloredTextSub = ChatColor.translateAlternateColorCodes('&', subtitle);
                    format = BACore.SetConfig().getString("mention.format.everyone").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", everyone.getName()).replace("%message%", e.getMessage());
                    allmsg = e.getMessage();
                } while (!e.getMessage().contains("@" + format));
            } while (!e.getPlayer().hasPermission("surfing.mention.everyone"));

            allmsg = allmsg.replaceFirst("@(?i)" + format, BACore.SetConfig().getString("mention.options.everyone")).replace("%format%", format).replace("%mentioner%", e.getPlayer().getName()).replace("%player%", everyone.getName()).replace("%message%", e.getMessage()).replace("%e-format%", format);
            e.setMessage(ChatColor.translateAlternateColorCodes('&', allmsg));
            if (BACore.SetConfig().getBoolean("mention.config.sound")) {
                //Utils.SoundAPI(everyone, BACore.SetConfig().getString("mention.config.sound.set"));
                XSound.play(everyone, BACore.SetConfig().getString("mention.config.sound.set"));
            }

            if (BACore.SetConfig().getBoolean("mention.config.titles")) {
                CustomTitle.sendTitle(everyone, BACore.SetConfig().getInt("mention.options.title.fadein") * 20, BACore.SetConfig().getInt("mention.options.title.fadein") * 20, BACore.SetConfig().getInt("mention.options.title.fadeout") * 20, coloredText, coloredTextSub);
            }

            if (BACore.SetConfig().getBoolean("mention.config.actionbar")) {
                ActionBarAPI bar = new ActionBarAPI();
                bar.setMessage(ChatColor.translateAlternateColorCodes('&', BACore.SetConfig().getString("mention.messages.actionbar").replace("%mentioner%", e.getPlayer().getName()).replace("%player%", everyone.getName()).replace("%message%", e.getMessage())));
                bar.send(new Player[]{everyone});
            }
        }
    }
}
