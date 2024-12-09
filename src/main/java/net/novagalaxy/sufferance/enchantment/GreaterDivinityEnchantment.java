
package net.novagalaxy.sufferance.enchantment;

import net.novagalaxy.sufferance.init.SufferanceModItems;
import net.novagalaxy.sufferance.init.SufferanceModEnchantments;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.List;

public class GreaterDivinityEnchantment extends Enchantment {
	public GreaterDivinityEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, slots);
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return !List.of(SufferanceModEnchantments.LESSER_DIVINITY.get()).contains(ench);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		Item item = stack.getItem();
		return List.of(SufferanceModItems.DUSKS_EPITAPH.get()).contains(item);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isCurse() {
		return true;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}
}
