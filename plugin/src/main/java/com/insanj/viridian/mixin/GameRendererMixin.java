package com.insanj.viridian.mixin;

import com.insanj.viridian.ViridianMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.BlockPos;

// import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.text.TextComponent;
// import net.minecraft.world.World;

import com.mojang.blaze3d.platform.GlStateManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

			GlStateManager.popMatrix();
		}
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
