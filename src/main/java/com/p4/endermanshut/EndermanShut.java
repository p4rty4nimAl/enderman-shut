package com.p4.endermanshut;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

import java.io.IOException;

import static com.p4.endermanshut.ConfigInit.init;

public class EndermanShut implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        try {
            ESCommand.register(ClientCommandManager.DISPATCHER);
            System.out.println("enderman-shut: Initialisation " + (init() ? "Success!" : "Failed!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
