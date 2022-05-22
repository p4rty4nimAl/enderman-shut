package com.p4.endermanshut;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.DoubleArgumentType.getDouble;
import static com.p4.endermanshut.ConfigInit.endermanVolume;
import static com.p4.endermanshut.ConfigInit.turnOffScreams;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class ESCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("esa")
                .then(
                        literal("volume").then(
                                argument("volume", DoubleArgumentType.doubleArg(0.01D, 1D))
                                        .executes(ctx -> {
                                            ConfigInit.endermanVolume = getDouble(ctx, "volume");
                                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new TranslatableText("endermanshut.volume.success", (endermanVolume * 100) + "%"));
                                            return 1;
                                        })
                        ).then(
                                literal("OFF")
                                        .executes(ctx -> {
                                            ConfigInit.endermanVolume = 0D;
                                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new TranslatableText("endermanshut.volume.success", new TranslatableText("endermanshut.text.off")));
                                            return 1;
                                        })
                        )
                )
                .then(
                        literal("ambient").then(
                                argument("ambient", BoolArgumentType.bool())
                                        .executes(ctx -> {
                                            turnOffScreams = getBool(ctx, "ambient");
                                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new TranslatableText("endermanshut.ambient.success", turnOffScreams ? new TranslatableText("endermanshut.text.on") : new TranslatableText("endermanshut.text.off")));
                                            return 1;
                                        })
                        )
                )
        );
    }
}
