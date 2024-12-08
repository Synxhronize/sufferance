
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.novagalaxy.sufferance.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class SufferanceModTabs {
	public static CreativeModeTab TAB_SUFFERANCE;

	public static void load() {
		TAB_SUFFERANCE = new CreativeModeTab("tabsufferance") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(SufferanceModItems.DUSKS_EPITAPH.get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
