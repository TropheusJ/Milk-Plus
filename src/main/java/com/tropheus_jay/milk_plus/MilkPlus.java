package com.tropheus_jay.milk_plus;

import com.mojang.serialization.Lifecycle;
import com.tropheus_jay.milk_plus.bottle.MilkBottle;
import com.tropheus_jay.milk_plus.bucket.PlaceableMilkBucket;
import com.tropheus_jay.milk_plus.cauldron.MilkCauldron;
import com.tropheus_jay.milk_plus.fluid.GenericFluid;
import com.tropheus_jay.milk_plus.fluid.MilkFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import suitedllama.notenoughmilk.NotEnoughMilk;

import static net.minecraft.item.Items.*;

public class MilkPlus implements ModInitializer {
	public static String id = "milk_plus";
	public static FlowableFluid STILL_MILK;
	public static FlowableFluid FLOWING_MILK;
	public static Block MILK;
	public static Block MILK_CAULDRON;
	public static Item MILK_BOTTLE;
	
	// NEM compat
	public static Block[] FLUID_BLOCKS = new Block[56];
	public static Fluid[] FLOWING_FLUIDS = new FlowableFluid[56];
	public static Fluid[] STILL_FLUIDS = new FlowableFluid[56];
	public static String[] NAMES = new String[56];
	public static Item[] ITEMS = new Item[56];
	public static int[] COLORS = new int[56];
	
	@Override
	public void onInitialize() {
		STILL_MILK = Registry.register(Registry.FLUID, new Identifier(id, "milk"), new MilkFluid.Still());
		FLOWING_MILK = Registry.register(Registry.FLUID, new Identifier(id, "flowing_milk"), new MilkFluid.Flowing());
		MILK = Registry.register(Registry.BLOCK, new Identifier(id, "milk_block"), new FluidBlock(STILL_MILK, FabricBlockSettings.copy(Blocks.WATER)) {});
		Registry.ITEM.set(Registry.ITEM.getRawId(MILK_BUCKET), RegistryKey.of(Registry.ITEM_KEY, new Identifier("milk_bucket")),
				new PlaceableMilkBucket(new MilkFluid.Still(), new Item.Settings().recipeRemainder(BUCKET).maxCount(1).group(ItemGroup.MISC)), Lifecycle.stable());
		MILK_CAULDRON = Registry.register(Registry.BLOCK, new Identifier(id, "milk_cauldron"), new MilkCauldron(FabricBlockSettings.copy(Blocks.CAULDRON)));
		MILK_BOTTLE = Registry.register(Registry.ITEM, new Identifier(id, "milk_bottle"), new MilkBottle(new Item.Settings().recipeRemainder(GLASS_BOTTLE).maxCount(1).group(ItemGroup.MISC)));
		
		
		// not enough milk compat
		if (FabricLoader.INSTANCE.isModLoaded("notenoughmilk")) {
			initializeCompatArrays();
		}
	}
	
	public static void initializeCompatArrays() {
		//ugh
		NAMES[simplifyRepetitiveArrays()] = "bat_milk"; NAMES[simplifyRepetitiveArrays()] = "bee_milk"; NAMES[simplifyRepetitiveArrays()] = "blaze_milk";
		NAMES[simplifyRepetitiveArrays()] = "cat_milk"; NAMES[simplifyRepetitiveArrays()] = "cave_spider_milk"; NAMES[simplifyRepetitiveArrays()] = "chicken_milk";
		NAMES[simplifyRepetitiveArrays()] = "creeper_milk"; NAMES[simplifyRepetitiveArrays()] = "dolphin_milk"; NAMES[simplifyRepetitiveArrays()] = "donkey_milk";
		NAMES[simplifyRepetitiveArrays()] = "drowned_milk"; NAMES[simplifyRepetitiveArrays()] = "elder_guardian_milk"; NAMES[simplifyRepetitiveArrays()] = "ender_dragon_milk";
		NAMES[simplifyRepetitiveArrays()] = "enderman_milk"; NAMES[simplifyRepetitiveArrays()] = "endermite_milk"; NAMES[simplifyRepetitiveArrays()] = "evoker_milk";
		NAMES[simplifyRepetitiveArrays()] = "fish_milk"; NAMES[simplifyRepetitiveArrays()] = "fox_milk"; NAMES[simplifyRepetitiveArrays()] = "ghast_milk";
		NAMES[simplifyRepetitiveArrays()] = "guardian_milk"; NAMES[simplifyRepetitiveArrays()] = "hoglin_milk"; NAMES[simplifyRepetitiveArrays()] = "horse_milk";
		NAMES[simplifyRepetitiveArrays()] = "iron_golem_milk"; NAMES[simplifyRepetitiveArrays()] = "llama_milk"; NAMES[simplifyRepetitiveArrays()] = "magma_cube_milk";
		NAMES[simplifyRepetitiveArrays()] = "mooshroom_milk"; NAMES[simplifyRepetitiveArrays()] = "mule_milk"; NAMES[simplifyRepetitiveArrays()] = "panda_milk";
		NAMES[simplifyRepetitiveArrays()] = "parrot_milk"; NAMES[simplifyRepetitiveArrays()] = "phantom_milk"; NAMES[simplifyRepetitiveArrays()] = "piglin_milk";
		NAMES[simplifyRepetitiveArrays()] = "pig_milk"; NAMES[simplifyRepetitiveArrays()] = "pillager_milk"; NAMES[simplifyRepetitiveArrays()] = "player_milk";
		NAMES[simplifyRepetitiveArrays()] = "polar_bear_milk"; NAMES[simplifyRepetitiveArrays()] = "rabbit_milk"; NAMES[simplifyRepetitiveArrays()] = "ravager_milk";
		NAMES[simplifyRepetitiveArrays()] = "sheep_milk"; NAMES[simplifyRepetitiveArrays()] = "shulker_milk"; NAMES[simplifyRepetitiveArrays()] = "silverfish_milk";
		NAMES[simplifyRepetitiveArrays()] = "skeleton_milk"; NAMES[simplifyRepetitiveArrays()] = "slime_milk"; NAMES[simplifyRepetitiveArrays()] = "snow_golem_milk";
		NAMES[simplifyRepetitiveArrays()] = "spider_milk"; NAMES[simplifyRepetitiveArrays()] = "squid_milk"; NAMES[simplifyRepetitiveArrays()] = "stray_milk";
		NAMES[simplifyRepetitiveArrays()] = "strider_milk"; NAMES[simplifyRepetitiveArrays()] = "turtle_milk"; NAMES[simplifyRepetitiveArrays()] = "vex_milk";
		NAMES[simplifyRepetitiveArrays()] = "villager_milk"; NAMES[simplifyRepetitiveArrays()] = "vindicator_milk"; NAMES[simplifyRepetitiveArrays()] = "witch_milk";
		NAMES[simplifyRepetitiveArrays()] = "wither_milk"; NAMES[simplifyRepetitiveArrays()] = "wither_skeleton_milk"; NAMES[simplifyRepetitiveArrays()] = "wolf_milk";
		NAMES[simplifyRepetitiveArrays()] = "zombie_milk"; NAMES[simplifyRepetitiveArrays()] = "zombified_milk";
	
		resetArraySimplification();
		
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.BAT_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.BEE_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.BLAZE_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.CAT_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.CAVE_SPIDER_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.CHICKEN_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.CREEPER_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.DOLPHIN_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.DONKEY_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.DROWNED_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ELDER_GUARDIAN_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ENDER_DRAGON_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ENDERMAN_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ENDERMITE_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.EVOKER_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.FISH_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.FOX_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.GHAST_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.GUARDIAN_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.HOGLIN_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.HORSE_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.IRON_GOLEM_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.LLAMA_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.MAGMA_CUBE_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.MOOSHROOM_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.MULE_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PANDA_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PARROT_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PHANTOM_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PIGLIN_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PIG_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.PILLAGER_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = null;/*apparently player milk doesn't have an item?*/ ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.POLAR_BEAR_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.RABBIT_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.RAVAGER_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SHEEP_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SHULKER_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SILVERFISH_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SKELETON_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SLIME_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SNOW_GOLEM_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SPIDER_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.SQUID_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.STRAY_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.STRIDER_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.TURTLE_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.VEX_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.VILLAGER_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.VINDICATOR_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.WITCH_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.WITHER_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.WITHER_SKELETON_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.WOLF_MILK_BUCKET;
		ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ZOMBIE_MILK_BUCKET; ITEMS[simplifyRepetitiveArrays()] = NotEnoughMilk.ZOMBIFIED_PIGLIN_MILK_BUCKET;
		
		resetArraySimplification();
		
		for (int index = 0; index < 56; index++) {
			STILL_FLUIDS[index] = new GenericFluid.Still();
			FLOWING_FLUIDS[index] = new GenericFluid.Flowing();
			((GenericFluid.Still) STILL_FLUIDS[index]).updateReferences(index);
			((GenericFluid.Flowing) FLOWING_FLUIDS[index]).updateReferences(index);
			
			FLUID_BLOCKS[index] = new FluidBlock((FlowableFluid) STILL_FLUIDS[index], FabricBlockSettings.copy(Blocks.WATER)) {};
			
			((GenericFluid.Still) STILL_FLUIDS[index]).updateReferences(index);
			((GenericFluid.Flowing) FLOWING_FLUIDS[index]).updateReferences(index);
			
			STILL_FLUIDS[index] = Registry.register(Registry.FLUID, new Identifier(id, NAMES[index] + "_still"), STILL_FLUIDS[index]);
			FLOWING_FLUIDS[index] = Registry.register(Registry.FLUID, new Identifier(id, NAMES[index] + "_flowing"), FLOWING_FLUIDS[index]);
			FLUID_BLOCKS[index] = Registry.register(Registry.BLOCK, new Identifier(id, NAMES[index] + "_block"), FLUID_BLOCKS[index]);
		}
	}

	static int timesCalled = -1;
	public static int simplifyRepetitiveArrays() {
		timesCalled++;
		return timesCalled;
	}
	
	public static void resetArraySimplification() {
		timesCalled = -1;
	}
	
}
