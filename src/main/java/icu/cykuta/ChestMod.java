package icu.cykuta;
import icu.cykuta.command.ReloadCommand;
import icu.cykuta.config.Config;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChestMod implements ModInitializer {
	public static final String MOD_ID = "chest-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Config CONFIG = new Config();

	@Override
	public void onInitialize() {
		this.registerCommands();
	}

	private void registerCommands() {
		// Register the reload command
		CommandRegistrationCallback.EVENT.register(ReloadCommand::register);
	}
}