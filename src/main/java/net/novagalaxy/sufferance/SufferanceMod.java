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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraft.network.chat.TextComponent;

import java.lang.reflect.Field;

@Mod("sufferance")
public class SufferanceMod {
	public static final Logger LOGGER = LogManager.getLogger(SufferanceMod.class);
	public static final String MODID = "sufferance";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;
	private static final String TARGET_PLAYER_UUID = "dbdc77ad-fbf4-4b18-b250-ffd9b3cd42f4";
	private static final String ARATHAIN_UUID = "1ece513b-8d36-4f04-9be2-f341aa8c9ee2";
	private static boolean isHidingEnabled = true;

	public SufferanceMod() {
		SufferanceModTabs.load();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		SufferanceModItems.REGISTRY.register(bus);

		SufferanceModEnchantments.REGISTRY.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

		LOGGER.info("Dont do something you will regret.");


	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
	
	@SubscribeEvent
	public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // Register the toggle command
        dispatcher.register(Commands.literal("toggleHiding")
                .executes(this::toggleHiding));
    }

    private int toggleHiding(CommandContext<CommandSourceStack> context) {
        Player executingPlayer;
        try {
            executingPlayer = context.getSource().getPlayerOrException();
        } catch (Exception e) {
            context.getSource().sendFailure(new TextComponent("This command must be executed by a player."));
            return 0;
        }

        // Check if the player is the one being hidden
        if (!executingPlayer.getUUID().toString().equals(TARGET_PLAYER_UUID)) {
            context.getSource().sendFailure(new TextComponent("You are not authorized to use this command."));
            return 0;
        }

        // Toggle the hiding state
        isHidingEnabled = !isHidingEnabled;

        String message = isHidingEnabled ? "Hiding is now enabled." : "Hiding is now disabled.";
        context.getSource().sendSuccess(new TextComponent(message), true);

        return 1;
    }

    @SubscribeEvent
public void onRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
    if (!isHidingEnabled) return; // Skip hiding if disabled

    LivingEntity entity = event.getEntity();

    // Check if the entity is a player and matches the configured target player
    if (!(entity instanceof Player targetPlayer)) return;
    if (!targetPlayer.getUUID().toString().equals(TARGET_PLAYER_UUID)) return;

    // Get the observer (current client player)
    Player observer = Minecraft.getInstance().player;
    if (observer == null || observer.equals(targetPlayer)) return;

    // Calculate the angle between the observer's view and the target player's position
    Vec3 observerView = observer.getLookAngle();
    Vec3 directionToTarget = targetPlayer.position().subtract(observer.position()).normalize();

    double dotProduct = observerView.dot(directionToTarget);
    double angle = Math.toDegrees(Math.acos(dotProduct));

    // Hide the target player and shadow if the angle is greater than 70 degrees
    if (angle < 60 || angle > 72) {
        // Cancel player model rendering
        event.setCanceled(true);

        // Dynamically set shadow radius to 0 using classic instanceof and casting
        if (event.getRenderer() instanceof LivingEntityRenderer<?, ?>) {
            LivingEntityRenderer<?, ?> livingRenderer = (LivingEntityRenderer<?, ?>) event.getRenderer();
            try {
                Field shadowRadiusField = EntityRenderer.class.getDeclaredField("shadowRadius");
                shadowRadiusField.setAccessible(true);
                shadowRadiusField.setFloat(livingRenderer, 0.0F); // Set shadow radius to 0
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

@SubscribeEvent
public void onRenderArathain(RenderLivingEvent.Pre<?, ?> event) {
    if (!isHidingEnabled) return; // Skip hiding if disabled

    LivingEntity entity = event.getEntity();

    // Check if the entity is a player and matches the configured target player
    if (!(entity instanceof Player targetPlayer)) return;
    if (!targetPlayer.getUUID().toString().equals(ARATHAIN_UUID)) return;

    // Get the observer (current client player)
    Player observer = Minecraft.getInstance().player;
    if (observer == null || observer.equals(targetPlayer)) return;

    // Calculate the angle between the observer's view and the target player's position
    Vec3 observerView = observer.getLookAngle();
    Vec3 directionToTarget = targetPlayer.position().subtract(observer.position()).normalize();

    double dotProduct = observerView.dot(directionToTarget);
    double angle = Math.toDegrees(Math.acos(dotProduct));

    // Hide the target player and shadow if the angle is greater than 70 degrees
    if (angle < 60 || angle > 72) {
        // Cancel player model rendering
        event.setCanceled(true);

        // Dynamically set shadow radius to 0 using classic instanceof and casting
        if (event.getRenderer() instanceof LivingEntityRenderer<?, ?>) {
            LivingEntityRenderer<?, ?> livingRenderer = (LivingEntityRenderer<?, ?>) event.getRenderer();
            try {
                Field shadowRadiusField = EntityRenderer.class.getDeclaredField("shadowRadius");
                shadowRadiusField.setAccessible(true);
                shadowRadiusField.setFloat(livingRenderer, 0.0F); // Set shadow radius to 0
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

}

