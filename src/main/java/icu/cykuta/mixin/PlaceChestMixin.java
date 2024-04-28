package icu.cykuta.mixin;

import icu.cykuta.chest.Chest;
import icu.cykuta.chest.ChestStatus;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class PlaceChestMixin {
    @Inject(at = @At("TAIL"), method = "place")
    private void onPlace(ItemPlacementContext context, CallbackInfoReturnable<ActionResult>info) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return;

        // Get chest from world
        BlockEntity blockEntity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (blockEntity == null) return;
        if (!(blockEntity instanceof ChestBlockEntity)) return;

        // Cast to Chest Interface
        Chest chest = (Chest) blockEntity;
        ChestStatus status = player.isCreative() ? ChestStatus.UNUSED : ChestStatus.USED;
        chest.setStatus(status);
    }
}
