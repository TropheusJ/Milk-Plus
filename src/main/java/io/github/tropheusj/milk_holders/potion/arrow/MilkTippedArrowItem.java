package io.github.tropheusj.milk_holders.potion.arrow;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MilkTippedArrowItem extends TippedArrowItem {
	public MilkTippedArrowItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return getTranslationKey();
	}

	@Override
	public ItemStack getDefaultStack() {
		return new ItemStack(this);
	}
}
