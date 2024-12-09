
package net.novagalaxy.sufferance.item;

import net.novagalaxy.sufferance.procedures.SearchPartyItemInHandTickProcedure;
import net.novagalaxy.sufferance.init.SufferanceModSounds;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SearchPartyItem extends RecordItem {
	public SearchPartyItem() {
		super(14, SufferanceModSounds.REGISTRY.get(new ResourceLocation("sufferance:search_party")), new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new TextComponent("Theres small writing on the disc"));
		list.add(new TextComponent("\\ \"Bound by the Charter.\""));
		list.add(new TextComponent("Wonder what that means?"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			SearchPartyItemInHandTickProcedure.execute(entity);
	}
}
