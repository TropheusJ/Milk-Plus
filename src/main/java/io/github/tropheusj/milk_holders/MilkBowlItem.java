package io.github.tropheusj.milk_holders;

import io.github.tropheusj.milk.Milk;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class MilkBowlItem extends StewItem {
	public MilkBowlItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack result = super.finishUsing(stack, world, user);
		Milk.tryRemoveRandomEffect(user);
		return result;
	}
}
