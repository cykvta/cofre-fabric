package icu.cykuta.mixin;

import icu.cykuta.chest.Chest;
import icu.cykuta.chest.ChestStatus;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class ChestStatusMixin implements Chest {
    private NbtCompound nbt;
    private ChestStatus status = ChestStatus.UNUSED;
    private ChestBlockEntity chest = (ChestBlockEntity) (Object) this;

    // Inject the readNbt method to read the status of the chest from NBT.
    @Inject(at = @At("HEAD"), method = "readNbt")
    private void readNbt(NbtCompound nbt, CallbackInfo info) {
        this.createNbt();
        ChestStatus status = nbt.contains("Used") ? ChestStatus.USED : ChestStatus.UNUSED;
        this.setStatus(status);
    }

    // Inject the writeNbt method to save the status of the chest to NBT.
    @Inject(at = @At("HEAD"), method = "writeNbt")
    private void writeNbt(NbtCompound nbt, CallbackInfo info) {
        this.nbt = nbt;
        this.writeNbtStatus();
    }

    @Override
    // used to save the status of the chest in a local variable.
    public void setStatus(ChestStatus value) {
        if (nbt == null) this.createNbt();
        this.status = value;
        this.writeNbtStatus();
    }

    // used to write the status of the chest to NBT.
    @Override
    public void writeNbtStatus() {
        if (this.status == ChestStatus.UNUSED) {
            nbt.remove("Used");
        }

        if (this.status == ChestStatus.USED) {
            nbt.putBoolean("Used", true);
        }
    }

    // used to check if the chest is used.
    @Override
    public boolean isUsed() {
        return this.status == ChestStatus.USED;
    }


    // used to create NBT cloning the original NBT.
    private void createNbt() {
        this.nbt = chest.createNbt();
    }
}
