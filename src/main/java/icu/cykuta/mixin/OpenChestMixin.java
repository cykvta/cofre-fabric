package icu.cykuta.mixin;

import icu.cykuta.ChestMod;
import icu.cykuta.chest.Chest;
import icu.cykuta.chest.ChestStatus;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ChestBlockEntity.class)
public class OpenChestMixin {
    private ChestBlockEntity chest = (ChestBlockEntity) (Object) this;

    @Inject(at = @At("HEAD"), method = "onOpen")
    private void onOpen(PlayerEntity player, CallbackInfo info) {
        Chest chest = (Chest) this.chest;

        // If the chest is already used, return.
        if (chest.isUsed()) return;

        // Set the status of the chest to USED.
        chest.setStatus(ChestStatus.USED);

        // Get biome of the chest.
        RegistryKey<Biome> biome = player.world.getBiome(this.chest.getPos()).getKey().get();
        String[] loot_list = ChestMod.CONFIG.getLootTable(biome);

        // If the loot list is empty, return.
        if (loot_list.length == 0) return;

        // Get random loot table for the list.
        Random random = new Random();
        Identifier loot = new Identifier(ChestMod.MOD_ID, loot_list[random.nextInt(loot_list.length)]);

        long seed = player.world.random.nextLong();
        this.chest.setLootTable(loot, seed);
    }
}
