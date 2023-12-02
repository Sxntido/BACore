package org.github.sxntido.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.sxntido.BACore;
import org.github.sxntido.tools.Utils;

public class Config extends YamlConfiguration {
    private File File;

    private String Path;

    private FileConfiguration FileC = null;

    public Config(String Path) {
        this.Path = Path + ".yml";
        this.File = new File(BACore.getInstance().getDataFolder(), this.Path);
        SaveDefault();
        Reload();
    }

    public void Reload() {
        try {
            load(this.File);
            ReloadUFT8();
        } catch (InvalidConfigurationException | IOException ex) {
            Utils.getLoggs("&cReload path: &d" + this.Path + " &cError ex>> &f" + ex, true);
        }
    }

    public void Save() {
        try {
            save(this.File);
        } catch (IOException ex) {
            Utils.getLoggs("&cSave path: &c" + this.Path + " &cError ex>> &f" + ex, true);
        }
    }

    public void SaveDefault() {
        try {
            if (!this.File.exists())
                BACore.getInstance().saveResource(this.Path, false);
        } catch (Exception ex) {
            Utils.getLoggs("&cSaveDefault path: &9" + this.Path + " &cError ex>> &f" + ex, true);
        }
    }

    public void ReloadUFT8() {
        if (this.FileC == null)
            this.File = new File(BACore.getInstance().getDataFolder(), this.Path);
        this.FileC = (FileConfiguration)YamlConfiguration.loadConfiguration(this.File);
        try {
            Reader defConfigStream = new InputStreamReader(BACore.getInstance().getResource(this.Path), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.FileC.setDefaults((Configuration)defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}