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
		
		// the pain is unbearable
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
		
		// end me
		COLORS[simplifyRepetitiveArrays()] = 0x524020; COLORS[simplifyRepetitiveArrays()] = 0xFFC328; COLORS[simplifyRepetitiveArrays()] = 0xF58026;
		COLORS[simplifyRepetitiveArrays()] = 0xF6EF9B; COLORS[simplifyRepetitiveArrays()] = 0x1A291C; COLORS[simplifyRepetitiveArrays()] = 0xFFFFFF;
		COLORS[simplifyRepetitiveArrays()] = 0x3DD13D; COLORS[simplifyRepetitiveArrays()] = 0x55B7CF; COLORS[simplifyRepetitiveArrays()] = 0x9E7857;
		COLORS[simplifyRepetitiveArrays()] = 0x32A76E; COLORS[simplifyRepetitiveArrays()] = 0x1D011B; COLORS[simplifyRepetitiveArrays()] = 0x1D011B;
		COLORS[simplifyRepetitiveArrays()] = 0x2C0F32; COLORS[simplifyRepetitiveArrays()] = 0xFEE04E; COLORS[simplifyRepetitiveArrays()] = 0x0EF6F6;
		COLORS[simplifyRepetitiveArrays()] = 0xFFD2A9; COLORS[simplifyRepetitiveArrays()] = 0xC8B089; COLORS[simplifyRepetitiveArrays()] = 0x49C386;
		COLORS[simplifyRepetitiveArrays()] = 0xB68572; COLORS[simplifyRepetitiveArrays()] = 0xCB9273; COLORS[simplifyRepetitiveArrays()] = 0xEBDACB;
		COLORS[simplifyRepetitiveArrays()] = 0xF3EDE8; COLORS[simplifyRepetitiveArrays()] = 0x8F3825; COLORS[simplifyRepetitiveArrays()] = 0x8F3825;
		COLORS[simplifyRepetitiveArrays()] = 0x9C6E3D; COLORS[simplifyRepetitiveArrays()] = 0xEFDA8C; COLORS[simplifyRepetitiveArrays()] = 0xC2E2B4;
		COLORS[simplifyRepetitiveArrays()] = 0xE3FCC8; COLORS[simplifyRepetitiveArrays()] = 0x1D283C; COLORS[simplifyRepetitiveArrays()] = 0xE3AAE1;
		COLORS[simplifyRepetitiveArrays()] = 0xA06761; COLORS[simplifyRepetitiveArrays()] = 0x270D20; COLORS[simplifyRepetitiveArrays()] = 0xD7C4B0;
		COLORS[simplifyRepetitiveArrays()] = 0x8097B1; COLORS[simplifyRepetitiveArrays()] = 0xB99B56; COLORS[simplifyRepetitiveArrays()] = 0x2A2D32;
		COLORS[simplifyRepetitiveArrays()] = 0xB49E8A; COLORS[simplifyRepetitiveArrays()] = 0xE2C4EB; COLORS[simplifyRepetitiveArrays()] = 0x626D76;
		COLORS[simplifyRepetitiveArrays()] = 0xDADADA; COLORS[simplifyRepetitiveArrays()] = 0x68BE6B; COLORS[simplifyRepetitiveArrays()] = 0xBFF0F0;
		COLORS[simplifyRepetitiveArrays()] = 0x500F0B; COLORS[simplifyRepetitiveArrays()] = 0x16171E; COLORS[simplifyRepetitiveArrays()] = 0xA3A5B4;
		COLORS[simplifyRepetitiveArrays()] = 0xEB635B; COLORS[simplifyRepetitiveArrays()] = 0x56A356; COLORS[simplifyRepetitiveArrays()] = 0x73BEBC;
		COLORS[simplifyRepetitiveArrays()] = 0xEBC7AB; COLORS[simplifyRepetitiveArrays()] = 0x144649; COLORS[simplifyRepetitiveArrays()] = 0x593674;
		COLORS[simplifyRepetitiveArrays()] = 0x1B1B1B; COLORS[simplifyRepetitiveArrays()] = 0x8E7965; COLORS[simplifyRepetitiveArrays()] = 0x618B43;
		COLORS[simplifyRepetitiveArrays()] = 0x1C4E22; COLORS[simplifyRepetitiveArrays()] = 0x716547;
		
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
