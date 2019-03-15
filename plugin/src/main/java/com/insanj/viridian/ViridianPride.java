package com.insanj.viridian;


public class ViridianPride {
  public static void chicken() {
    for (ViridianPridePacketPlayer packetPlayer : response.getPlayers()) {
        PlayerEntity player = packetPlayer.getPlayer();
        Map<String, Map<String, Double>> prideAreas = packetPlayer.getPrideAreas();
        BlockPos playerPos = player.getPos();
        for (String areaName: prideAreas.keySet()) {
            Map<String, Double> prideArea = prideAreas.get(areaName);
            Double diff = Math.abs(Math.abs(prideArea.get("x") - playerPos.getX()) + Math.abs(prideArea.get("y") - playerPos.getY()) + Math.abs(prideArea.get("z") - playerPos.getZ()));
            Double threshold = 50.0;
            if (diff <= threshold) {
              if (drewName == false) {
                  drewName = true;
                  client.textRenderer.drawWithShadow(player.getName(), 5, renderY, ViridianMod.config.hudColor);
                  renderY = renderY + renderDiff;
              }

              client.textRenderer.drawWithShadow(areaName, 10, renderY, ViridianMod.config.hudColor);
              renderY = renderY + renderDiff;
            }
        }
      }
    }

  }
}
