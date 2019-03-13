package com.insanj.viridian;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.dataformat.yaml.*;

public class ViridianConfig {

    public boolean showHud = true;
    public int hudColor = 0xeeeeee;

    public void saveConfig() {
        String configPath = FabricLoader.getInstance().getConfigDirectory() + "/" + ViridianMod.MOD_ID + ".json";
        File configFile = new File(configPath);
        String result = new Gson().toJson(this);
        try {
            FileOutputStream out = new FileOutputStream(configFile, false);

            out.write(result.getBytes());
            out.flush();
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readConfig() {
      YAMLMapper mapper = new YAMLMapper(new YAMLFactory());
      try {
          String configPath = FabricLoader.getInstance().getConfigDirectory() + "/" + ViridianMod.MOD_ID + ".yml";
          ViridianFile file = mapper.readValue(new File(configPath), ViridianFile.class);
          System.out.println(ReflectionToStringBuilder.toString(file, ToStringStyle.MULTI_LINE_STYLE));
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
