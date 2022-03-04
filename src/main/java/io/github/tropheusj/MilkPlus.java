package io.github.tropheusj;

import io.github.tropheusj.milk.Milk;
import io.github.tropheusj.milk.MilkCauldron;
import io.github.tropheusj.milk_holders.MilkBowlItem;
import io.github.tropheusj.milk_holders.potion.arrow.MilkTippedArrowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

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
		Milk.enableMilkFluid();
		Milk.enableMilkPlacing();
		Milk.enableAllMilkBottles();
		Milk.enableCauldron();
		MILK_ARROW = Registry.register(Registry.ITEM, id("milk_arrow"),
				new MilkTippedArrowItem((new FabricItemSettings().group(ItemGroup.COMBAT))));
		MILK_BOWL = Registry.register(Registry.ITEM, id("milk_bowl"),
				new MilkBowlItem(new FabricItemSettings().maxCount(1).group(ItemGroup.FOOD).food(new FoodComponent.Builder().alwaysEdible().build())));
		
		MilkCauldron.addInputToCauldronExchange(MILK_BOWL.getDefaultStack(), BOWL.getDefaultStack(), true);
		MilkCauldron.addOutputToItemExchange(BOWL.getDefaultStack(), MILK_BOWL.getDefaultStack(), true);
		
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
