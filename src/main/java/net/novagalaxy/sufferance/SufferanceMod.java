/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and it won't get overwritten.
 *    If you change your modid or package, you need to apply these changes to this file MANUALLY.
 *
 *    Settings in @Mod annotation WON'T be changed in case of the base mod element
 *    files lock too, so you need to set them manually here in such case.
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package net.novagalaxy.sufferance;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.novagalaxy.sufferance.init.SufferanceModTabs;
import net.novagalaxy.sufferance.init.SufferanceModItems;
import net.novagalaxy.sufferance.init.SufferanceModEnchantments;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraft.client.Minecraft;

@Mod("sufferance")
public class SufferanceMod {
	public static final Logger LOGGER = LogManager.getLogger(SufferanceMod.class);
	public static final String MODID = "sufferance";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;
	private static final String TARGET_PLAYER_NAME = "NovaGalaxy";

	public SufferanceMod() {
		SufferanceModTabs.load();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		SufferanceModItems.REGISTRY.register(bus);

		SufferanceModEnchantments.REGISTRY.register(bus);

	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
	
	@SubscribeEvent
		public void onRenderLiving(RenderLivingEvent.Pre<Player, ?> event) {
        if (event.getEntity() instanceof Player targetPlayer) {
            if (!targetPlayer.getName().getString().equalsIgnoreCase(TARGET_PLAYER_NAME)) return;

            Player observer = Minecraft.getInstance().player; // Get the observer (current client player)
            if (observer == null || observer.equals(targetPlayer)) return;

            Vec3 observerView = observer.getLookAngle();
            Vec3 directionToTarget = targetPlayer.position().subtract(observer.position()).normalize();

            double dotProduct = observerView.dot(directionToTarget);
            double angle = Math.toDegrees(Math.acos(dotProduct));

            if (angle > 70) {
                event.setCanceled(true); // Prevent rendering the target player
            }
        }
    }
}
