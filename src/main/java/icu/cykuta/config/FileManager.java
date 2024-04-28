package icu.cykuta.config;

import icu.cykuta.ChestMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private final ModContainer mod_container = FabricLoader.getInstance().getModContainer(ChestMod.MOD_ID).get();
    private final FabricLoader instance = FabricLoader.getInstance();
    private final Path config_dir = this.instance.getConfigDir().resolve(ChestMod.MOD_ID);

    // Get the config file and the loot tables folder.
    private final File config_file = this.config_dir.resolve("chest-mod-config.json").toFile();

    public FileManager() {
        this.verifyConfig();
    }

    private void verifyConfig() {
        // If the config file does not exist, create it.
        if (!this.config_file.exists()) {
            System.out.println("Config file not found, creating...");
            this.createConfig();
        }
    }

    private void createConfig() {
        // Get the source and destination of the config file.
        Path source = this.mod_container.findPath("config/chest-mod-config.json").get();
        Path config = this.config_file.toPath();

        // Create config file.
        config.getParent().toFile().mkdirs();

        try {
            Files.copy(source, config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getConfig_file() {
        return config_file;
    }
}
