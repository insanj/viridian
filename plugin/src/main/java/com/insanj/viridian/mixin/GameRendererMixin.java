package com.insanj.viridian.mixin;

import com.insanj.viridian.ViridianMod;
import com.insanj.viridian.ViridianPersistentState;

import java.util.Map;
import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.ClientGameSession;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.text.TextComponent;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.EnvironmentHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
@Mixin(MinecraftClientGame.class)
public class MinecraftClientGameMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClientGame;alphaFunc(IF)V"), method = "onStartGameSession")
	public void render(float float_1, long long_1, boolean boolean_1, CallbackInfo info) {


}*/

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;alphaFunc(IF)V"), method = "render")
	public void render(float float_1, long long_1, boolean boolean_1, CallbackInfo info) {

		MinecraftClient client = MinecraftClient.getInstance();
		if(!client.options.debugEnabled && ViridianMod.config.showHud) {
			BlockPos blockPos = new BlockPos(client.getCameraEntity().x, client.getCameraEntity().getBoundingBox().minY, client.getCameraEntity().z);
			double scaleFactor = client.window.getScaleFactor();
			GlStateManager.pushMatrix();
			GlStateManager.scaled(1 * scaleFactor, 1 * scaleFactor, 1 * scaleFactor);

			String facingString = getCardinalDirection(client.getCameraEntity().yaw);
			String ashString = String.format("%d fps %d %d %d %s", MinecraftClient.getCurrentFps(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), facingString);
			client.textRenderer.drawWithShadow(ashString, 5, 5, ViridianMod.config.hudColor);

			Chunk chunk = client.world.getWorldChunk(blockPos);
			TextComponent biomeInfoTextComponent = chunk.getBiome(blockPos).getTextComponent();
			String biomeInfoString = biomeInfoTextComponent.getFormattedText();
			client.textRenderer.drawWithShadow(biomeInfoString, 5, 15, ViridianMod.config.hudColor);

			// pride (TODO: make async?)
			try {
					float renderDiff = 10;
					float renderY = 15 + renderDiff;
					boolean drewName = false;

					ViridianPridePacketResponse response = getViridianPridePacketResponse();
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

			} catch (Exception e) {
				System.out.println(e.getStackTrace().toString());
			}

			GlStateManager.popMatrix();
		}
	}

	final class ViridianPridePacketResponse {
		private final List<ViridianPridePacketPlayer> players;
		public ViridianPridePacketResponse(List<ViridianPridePacketPlayer> players) { this.players = players; }
		public List<ViridianPridePacketPlayer> getPlayers() { return players; }
	}

	final class ViridianPridePacketPlayer {
		private final PlayerEntity player;
		private final Map<String, Map<String, Double>> prideAreas;
		public ViridianPridePacketPlayer(PlayerEntity player, Map<String, Map<String, Double>> prideAreas) { this.player = player; this.prideAreas = prideAreas; }
		public PlayerEntity getPlayer() { return player; }
		public Map<String, Map<String, Double>> getPrideAreas() { return prideAreas; }
	}

	private ViridianPridePacketResponse getViridianPridePacketResponse() {
			PlayerManager playerManager = server.getPlayerManager();
				String[] playerNames = playerManager.getPlayerNames()
									for (String playerName : playerNames) { // server.getUserName();
						PlayerEntity player = playerManager.getPlayer(playerName);
						ServerWorld serverWorld = player.getServerWorld();
						Map<String, Map<String, Double>> prideAreas = persis.getPrideAreas(serverWorld);
					}
					ServerWorld serverWorld;
					ViridianPersistentState persis = ViridianPersistentState.get(serverWorld);
					String worldKey = world.getSaveHandler().getWorldDir().getName();
					public static String keyForWorld(World world) {
						return world.getSaveHandler().getWorldDir().getName(); //getSaveHandler().readProperties().get();
					}
			return new ViridianPridePacketResponse(players, worlds);


			//	System.out.println("Pride time 2");
			//	EnvironmentHandler envHandler = FabricLoader.INSTANCE.getEnvironmentHandler()
			//	System.out.println("envHandler = " + envHandler.toString());
			//String worldKey = ViridianPersistentState.keyForWorld(player.getWorld());


			// client.getServer();
				//	if (server == null) { // CLIENT //
					//	System.out.println("about to getSession, remoteServer...");
						// ClientGameSession session = (ClientGameSession)client.getSession();
						 // getUsername() / getUuid() / .getGame().getSession();
					//	server = ServerNetworkIO.getServer();
						//	server = FabricLoader.getInstance().getEnvironmentHandler().getServerInstance();
				//	}
				// 							MinecraftServer server = envHandler.getServerInstance();


						//	System.out.println("server = " + server.toString());
						//	System.out.println("getPlayerManager = " + playerManager.toString());

					//		System.out.println("Player names = " + playerNames.toString());
	}

	// based off of defcon & worldedit https://www.spigotmc.org/threads/player-direction.175482/
	public String getCardinalDirection(double yaw) {
			 double rotation = (yaw - 180.0F) % 360.0F;
			 if (rotation < 0.0D) {
					 rotation += 360.0D;
			 }
			 if ((0.0D <= rotation) && (rotation < 22.5D)) {
					 return "N";
			 }
			 if ((22.5D <= rotation) && (rotation < 67.5D)) {
					 return "NE";
			 }
			 if ((67.5D <= rotation) && (rotation < 112.5D)) {
					 return "E";
			 }
			 if ((112.5D <= rotation) && (rotation < 157.5D)) {
					 return "SE";
			 }
			 if ((157.5D <= rotation) && (rotation < 202.5D)) {
					 return "S";
			 }
			 if ((202.5D <= rotation) && (rotation < 247.5D)) {
					 return "SW";
			 }
			 if ((247.5D <= rotation) && (rotation < 292.5D)) {
					 return "W";
			 }
			 if ((292.5D <= rotation) && (rotation < 337.5D)) {
					 return "NW";
			 }
			 if ((337.5D <= rotation) && (rotation < 360.0D)) {
					 return "N";
			 }
			 return null;
	 }
}
