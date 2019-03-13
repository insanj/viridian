package com.insanj.viridian;

import java.util.Map;

public class ViridianFile {
  // format of pride save files:
  // worlds:
  //    abcde-12345....: (1st map)
  //      Birch & Brick: (2nd map)
  //        x: .... (3rd map)
  public Map<String, Map<String, Map<String, String>>> worlds;
}
