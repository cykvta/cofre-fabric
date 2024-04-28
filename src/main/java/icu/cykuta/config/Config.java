package icu.cykuta.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Set;

public class Config {
    private final FileManager config = new FileManager();
    private final File file = this.config.getConfig_file();
    private final HashMap<String, JsonArray> config_map = new HashMap<>();

    public Config() {
        this.readJson();
    }

    // Read the config file.
    public void readJson() {
        try {
            JsonElement json = JsonParser.parseReader(new FileReader(this.file));
            Set<String> keys = json.getAsJsonObject().keySet();

            for (String key : keys) {
                this.config_map.put(key, json.getAsJsonObject().get(key).getAsJsonArray());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get the loot table of the biome.
    public String[] getLootTable(RegistryKey<Biome> biome) {
        String biome_name = biome.getValue().toString();

        // If the config contains the biome, return the loot table of the biome.
        if (this.config_map.containsKey(biome_name.toLowerCase())) {
            return jsonArrayParser(this.config_map.get(biome_name));
        } else {
            return jsonArrayParser(this.config_map.get("default"));
        }
    }

    // Convert JsonArray to String[].
    private String[] jsonArrayParser(JsonArray jsonArray) {
        String[] result = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = jsonArray.get(i).getAsString();
        }
        return result;
    }

    public void reload() {
        // Delete the old config map.
        this.config_map.clear();

        // Read the config file again.
        this.readJson();
    }
}
