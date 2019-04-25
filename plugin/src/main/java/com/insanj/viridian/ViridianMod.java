package com.insanj.viridian;

import com.google.gson.Gson;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ViridianMod implements ModInitializer {
    public static final String MOD_ID = "viridian";
    public static ViridianConfig config;

    @Override
    public void onInitialize() {
        String configPath = FabricLoader.getInstance().getConfigDirectory() + "/" + MOD_ID + ".json";

        Gson gson = new Gson();

        File configFile = new File(configPath);

        if(!configFile.exists()) {
            config = new ViridianConfig();
            String result = gson.toJson(config);
            try {
                FileOutputStream out = new FileOutputStream(configFile, false);

                out.write(result.getBytes());
                out.flush();
                out.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {

            try {
                config = gson.fromJson( new FileReader(configFile), ViridianConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                config = (config == null? new ViridianConfig() : config);
            }
        }


        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> serverCommandSourceCommandDispatcher.register(
                CommandManager.literal("viridian")
                        .executes(context -> {
                            config.showHud = !config.showHud;
                            config.saveConfig();
                            return 1;
                        })
        ));

        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> serverCommandSourceCommandDispatcher.register(
                CommandManager.literal("viridiancolor")
                        .then(CommandManager.argument("r", IntegerArgumentType.integer())
                                .then(CommandManager.argument("g", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("b", IntegerArgumentType.integer())
                                            .executes(context -> {
                                                int r = IntegerArgumentType.getInteger(context,"r");
                                                int g = IntegerArgumentType.getInteger(context,"g");
                                                int b = IntegerArgumentType.getInteger(context,"b");

                                                config.hudColor = b + (g << 8) + (r << 16);
                                                config.saveConfig();
                                                return 1;
                                }))))
        ));
    }
}
