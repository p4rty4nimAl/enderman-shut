package com.p4.endermanshut.mixin;

import com.p4.endermanshut.ESCommand;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {
    @Inject(method = "onCommandTree", at = @At("TAIL"))
    private void onOnCommandTree(CommandTreeS2CPacket packet, CallbackInfo ci) {
        ESCommand.register(ClientCommandManager.getActiveDispatcher());
    }
}
