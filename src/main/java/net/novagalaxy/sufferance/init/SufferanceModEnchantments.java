
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.novagalaxy.sufferance.init;

import net.novagalaxy.sufferance.enchantment.LesserDivinityEnchantment;
import net.novagalaxy.sufferance.SufferanceMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

public class SufferanceModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SufferanceMod.MODID);
	public static final RegistryObject<Enchantment> LESSER_DIVINITY = REGISTRY.register("lesser_divinity", () -> new LesserDivinityEnchantment());
}
