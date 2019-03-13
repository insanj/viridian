package com.insanj.viridian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.ServerCommandManager;
import com.google.gson.Gson;

public class ViridianMod implements ModInitializer {

    public static final String MOD_ID = "insanj_viridian";
    public static ViridianConfig config;

    public String configPath;
    public String pridePath;

    @Override
    public void onInitialize() {
        configPath = FabricLoader.getInstance().getConfigDirectory() + "/" + MOD_ID + ".json";
        pridePath = FabricLoader.getInstance().getConfigDirectory() + "/" + MOD_ID + "-pride.json";

        File configFile = new File(configPath);
        if(!configFile.exists()) {
            config = ViridianConfig.writeConfigToFile(configFile);
        }
        else {
            config = ViridianConfig.configFromFile(configFile);
        }

        // experimental pride features
        File prideFile = new File(pridePath);
        ViridianPride pride = ViridianPride.configFromFile(prideFile);
        System.out.println(pride.worlds.toString());

        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> serverCommandSourceCommandDispatcher.register(
                ServerCommandManager.literal("viridian")
                        .executes(context -> {
                            config.showHud = !config.showHud;
                            config.saveConfig(configPath);
                            return 1;
                        })
        ));

        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> serverCommandSourceCommandDispatcher.register(
                ServerCommandManager.literal("vcolor")
                        .then(ServerCommandManager.argument("r", IntegerArgumentType.integer())
                                .then(ServerCommandManager.argument("g", IntegerArgumentType.integer())
                                        .then(ServerCommandManager.argument("b", IntegerArgumentType.integer())
                                            .executes(context -> {
                                                int r = IntegerArgumentType.getInteger(context,"r");
                                                int g = IntegerArgumentType.getInteger(context,"g");
                                                int b = IntegerArgumentType.getInteger(context,"b");

                                                config.hudColor = b + (g << 8) + (r << 16);
                                                config.saveConfig(configPath);
                                                return 1;
                                }))))
        ));
    }
}
