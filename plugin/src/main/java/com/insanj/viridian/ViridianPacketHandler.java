package com.insanj.viridian;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.Packet;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.fabric.impl.network.ServerSidePacketRegistryImpl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ViridianPacketHandler {
		private static final String VIRIDIAN_PACKET_ID_STRING = "VIRIDIAN_PACKET_ID_STRING";
		private static final Identifier VIRIDIAN_PACKET_ID = Identifier.create(VIRIDIAN_PACKET_ID_STRING);

		public static void sendToServer(ViridianPacket packet, GenericFutureListener<? extends Future<? super Void>> completionListener) {
			 	PacketByteBuf buffer = PacketByteBuf.readCompoundTag(packet.toTag());
				ClientSidePacketRegistry.INSTANCE.sendToServer(VIRIDIAN_PACKET_ID, buffer, completionListener);
		}

		//public static ViridianPacket createViridianPacket() {
		/*		PlayerManager playerManager = server.getPlayerManager();
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
				return new ViridianPridePacketResponse(players, worlds);*/

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
}
