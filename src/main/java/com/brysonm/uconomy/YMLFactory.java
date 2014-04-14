package com.brysonm.uconomy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class YMLFactory {

    public static YML buildYML(String name, JavaPlugin main) {
        YML yml = new YML(name, main);
        return yml;
    }

    public static class YML {

        private FileConfiguration config;
        private File file;

        private String name;
        private JavaPlugin main;

        private String path;

        public YML(String name, JavaPlugin main) {
            this.name = name;
            this.main = main;

            this.path = this.name + ".yml";

            this.file = new File(main.getDataFolder(), path);
        }

        public FileConfiguration getConfig() {
            if(config == null) {
                reloadConfig();
            }
            return config;
        }

        public void saveConfig() {
            if(config == null || file == null) {
                return;
            }
            try {
                getConfig().save(file);
            } catch(IOException ex) {
                main.getLogger().log(Level.SEVERE, "Could not save " + name + " to file", ex);
            }
        }

        public void reloadConfig() {
            if(file == null) {
                file = new File(main.getDataFolder(), path);
            }
            config = YamlConfiguration.loadConfiguration(file);
            InputStream fileStream = main.getResource(path);
            if(fileStream != null) {
                YamlConfiguration configStream = YamlConfiguration.loadConfiguration(fileStream);
                config.setDefaults(configStream);
            }
        }

        public void saveDefaultConfig() {
            if(file == null) {
                file = new File(main.getDataFolder(), path);
            }
            if(!file.exists()) {
                main.saveResource(path, false);
            }
        }

    }
}