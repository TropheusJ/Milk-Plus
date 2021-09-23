package com.tropheus_jay.milk_plus;

import com.tropheus_jay.milk_plus.bottle.MilkBottle;
import com.tropheus_jay.milk_plus.cauldron.MilkCauldron;
import com.tropheus_jay.milk_plus.fluid.MilkFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.item.Items.GLASS_BOTTLE;

public class MilkPlus implements ModInitializer {
	public static FlowableFluid STILL_MILK;
	public static FlowableFluid FLOWING_MILK;
	public static Block MILK;
	public static Block MILK_CAULDRON;
	public static Item MILK_BOTTLE;
	
	@Override
	public void onInitialize() {
		STILL_MILK = Registry.register(Registry.FLUID, new Identifier("milk_plus", "milk"), new MilkFluid.Still());
		FLOWING_MILK = Registry.register(Registry.FLUID, new Identifier("milk_plus", "flowing_milk"), new MilkFluid.Flowing());
		MILK = Registry.register(Registry.BLOCK, new Identifier("milk_plus", "milk_block"),
				new FluidBlock(STILL_MILK, FabricBlockSettings.copy(Blocks.WATER).mapColor(MapColor.WHITE)) {});
		MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier("milk_plus", "milk_bottle"),
				new MilkBottle(new Item.Settings().recipeRemainder(GLASS_BOTTLE).maxCount(1).group(ItemGroup.MISC)));
		
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, MilkCauldron.FILL_FROM_BUCKET);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(MILK_BOTTLE, MilkCauldron.FILL_FROM_BOTTLE);
		
		MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier("milk_plus", "milk_cauldron"),
				new MilkCauldron(FabricBlockSettings.copy(Blocks.CAULDRON)));
	}
}
