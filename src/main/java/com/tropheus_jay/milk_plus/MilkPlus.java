package com.tropheus_jay.milk_plus;

import com.tropheus_jay.milk_plus.milk_holders.MilkBowlItem;
import com.tropheus_jay.milk_plus.milk_holders.potion.arrow.MilkTippedArrowItem;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.tropheusj.milk.Milk.STILL_MILK;
import static net.minecraft.item.Items.BOWL;

public class MilkPlus implements ModInitializer {
	public static final String ID = "milk_plus";
	public static Item MILK_ARROW;
	public static Item MILK_BOWL;
	
	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		Milk.enableAllMilkBottles();
		Milk.enableCauldron();
		MILK_ARROW = Registry.register(Registry.ITEM, id("milk_arrow"),
				new MilkTippedArrowItem((new FabricItemSettings()).group(ItemGroup.COMBAT)));
		MILK_BOWL = Registry.register(Registry.ITEM, id("milk_bowl"),
				new MilkBowlItem((new FabricItemSettings()).maxCount(1).group(ItemGroup.FOOD).food(FoodComponents.MUSHROOM_STEW)));
		
		initFluidApi();
	}
	
	@SuppressWarnings("UnstableApiUsage")
	public void initFluidApi() {
		FluidStorage.combinedItemApiProvider(MILK_BOWL).register(context ->
				new FullItemFluidStorage(context, bowl -> ItemVariant.of(BOWL), FluidVariant.of(STILL_MILK), FluidConstants.BOTTLE)
		);
		FluidStorage.combinedItemApiProvider(BOWL).register(context ->
				new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(MILK_BOWL), STILL_MILK, FluidConstants.BOTTLE)
		);
	}
}
