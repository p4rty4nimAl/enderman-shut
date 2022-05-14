package com.p4.endermanshut.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.p4.endermanshut.ConfigInit.endermanVolume;
import static com.p4.endermanshut.ConfigInit.turnOffScreams;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin extends HostileEntity {
    @Shadow
    private int lastAngrySoundAge = -2147483648;

    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "playAngrySound()V", at = @At("HEAD"), cancellable = true)
    public void onPlayAngrySound(CallbackInfo ci) {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), (float) (2.5F * endermanVolume), 1.0F, false);
                ci.cancel();
            }
        }
    }
    @Inject(method = "teleportTo(DDD)Z", at = @At("HEAD"), cancellable = true)
    public void onTeleportTo(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while(mutable.getY() > this.world.getBottomY() && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = this.teleport(x, y, z, true);
            if (bl3 && !this.isSilent()) {
                this.world.playSound((PlayerEntity)null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), (float) (1.0F * endermanVolume), 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, (float) (1.0F * endermanVolume), 1.0F);
            }

            cir.setReturnValue(bl3);
        } else {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "getAmbientSound()Lnet/minecraft/sound/SoundEvent;", at = @At("HEAD"), cancellable = true)
    public void onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (!turnOffScreams) cir.setReturnValue(SoundEvents.ENTITY_ENDERMAN_AMBIENT);
    }
}
