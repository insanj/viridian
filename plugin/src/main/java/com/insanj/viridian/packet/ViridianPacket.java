package com.insanj.viridian.packet;

import java.util.List;

class ViridianPacket {
    private Map<String, List<String>> players;
    public ViridianPacket(List<Pride> players) {
      this.players = players;
    }
    public Map<String, List<String>> getPlayers() { return players; };

    public CompoundTag toTag() {   }
    public static ViridianPacket fromTag(CompoundTag tag) {  }
}

/*

	public void fromTag(CompoundTag compoundTag) {
			CompoundTag worldsTag = (CompoundTag) compoundTag.getTag("pride_worlds");
			Set<String> worldKeys = worldsTag.getKeys();

			for (String key : worldKeys) {
					CompoundTag areasTag = (CompoundTag) worldsTag.getTag(key);
					Set<String> areaNames = areasTag.getKeys();
					Map<String, Map<String, Double>> parsedAreas = new HashMap();

					for (String areaName : areaNames) {
							CompoundTag area = (CompoundTag) areasTag.getTag(areaName); //areasTag.getList(areaName, NbtType.COMPOUND);
							Map<String, Double> areaMap = new HashMap();
							DoubleTag x = (DoubleTag) area.getTag("x");
							DoubleTag y = (DoubleTag) area.getTag("y");
							DoubleTag z = (DoubleTag) area.getTag("z");

							areaMap.put("x", x.getDouble());
							areaMap.put("y", y.getDouble());
							areaMap.put("z", z.getDouble());
							parsedAreas.put(areaName, areaMap);
					}

					worlds.put(key, parsedAreas);
			}
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
			CompoundTag worldsTag = new CompoundTag();

			for (String key : worlds.keySet()) {
				Map<String, Map<String, Double>> areas = worlds.get(key);
				CompoundTag areasTags = new CompoundTag();

				for (String areaName : areas.keySet()) {
						Map<String, Double> areaMap = areas.get(areaName);
						CompoundTag areaTag = new CompoundTag();

						DoubleTag x = new DoubleTag(areaMap.get("x"));
						DoubleTag y = new DoubleTag(areaMap.get("y"));
						DoubleTag z = new DoubleTag(areaMap.get("z"));

						areaTag.put("x", x);
						areaTag.put("y", y);
						areaTag.put("z", z);

						areasTags.put(areaName, areaTag);
				}

				worldsTag.put(key, areasTags);
			}

			compoundTag.put("pride_worlds", worldsTag);
			return compoundTag;
	}
  */
