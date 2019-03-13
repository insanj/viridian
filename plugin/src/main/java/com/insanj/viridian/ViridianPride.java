package com.insanj.viridian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.fabricmc.loader.api.FabricLoader;
import com.google.gson.Gson;

public class ViridianPride {
    // format of pride save files:
    // worlds:
    //    abcde-12345....: (1st map)
    //      Birch & Brick: (2nd map)
    //        x: .... (3rd map)
    public Map<String, Map<String, Map<String, String>>> worlds;

    public static ViridianPride configFromFile(File configFile) {
      Gson gson = new Gson();
      ViridianPride config = null;
      try {
          config = gson.fromJson( new FileReader(configFile), ViridianPride.class);
      } catch (Exception e) {
          e.printStackTrace();
      }
      finally {
          return (config == null ? new ViridianPride() : config);
      }
    }

    public static ViridianPride writeConfigToFile(File configFile) {
      Gson gson = new Gson();
      ViridianPride config = new ViridianPride();
      String result = gson.toJson(config);
      try {
          FileOutputStream out = new FileOutputStream(configFile, false);

          out.write(result.getBytes());
          out.flush();
          out.close();

      } catch (IOException ex) {
          ex.printStackTrace();
      }

      return config;
    }

    public void saveConfig(String configPath) {
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
}
