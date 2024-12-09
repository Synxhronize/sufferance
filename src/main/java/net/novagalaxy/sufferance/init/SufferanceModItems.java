
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.novagalaxy.sufferance.init;

import net.novagalaxy.sufferance.item.SearchPartyItem;
import net.novagalaxy.sufferance.item.DusksEpitaphItem;
import net.novagalaxy.sufferance.SufferanceMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

public class SufferanceModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SufferanceMod.MODID);
	public static final RegistryObject<Item> SEARCH_PARTY = REGISTRY.register("search_party", () -> new SearchPartyItem());
	public static final RegistryObject<Item> DUSKS_EPITAPH = REGISTRY.register("dusks_epitaph", () -> new DusksEpitaphItem());
}
