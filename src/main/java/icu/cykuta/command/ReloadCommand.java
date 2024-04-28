package icu.cykuta.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import icu.cykuta.ChestMod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReloadCommand {
    /*
        Better way to register commands create a command class and extend any command class from the new class.
     */

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("chestloot")
                .then(CommandManager.literal("reload").executes(ReloadCommand::reload)));
    }

    private static int reload(CommandContext<ServerCommandSource> ctx) {
        // Create a new Config instance and assign it to the CONFIG field
        ChestMod.CONFIG.reload();

        // Get player's name
        Text playerName = ctx.getSource().getPlayer().getName();

        // If the player is null, set the player's name to "Server"
        if (playerName == null) {
            playerName = Text.of("Server");
        }

        // Send a message to the player
        ctx.getSource().sendFeedback(Text.of(playerName.getString() + " Reloaded config"), true);
        return 1;
    }
}
