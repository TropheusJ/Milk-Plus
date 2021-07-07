package com.tropheus_jay.milk_plus.cauldron;

import com.tropheus_jay.milk_plus.MilkPlus;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

public class MilkCauldron extends LeveledCauldronBlock {
	public static final Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	public static final CauldronBehavior FILL_FROM_BUCKET = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, MilkPlus.MILK_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY);
	public static final CauldronBehavior FILL_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() == Blocks.CAULDRON || (state.get(LEVEL) != 3 && stack.getItem() == MilkPlus.MILK_BOTTLE)) {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				if (state.getBlock() == Blocks.CAULDRON) {
					world.setBlockState(pos, MilkPlus.MILK_CAULDRON.getDefaultState().with(LEVEL, 1));
				} else {
					world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL));
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	};
	public static final CauldronBehavior EMPTY_TO_BUCKET = (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), statex -> statex.get(LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_BOTTLE = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(MilkPlus.MILK_BOTTLE)));
			player.incrementStat(Stats.USE_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
		}
		return ActionResult.success(world.isClient);
	};
	public static final CauldronBehavior MILKIFY_DYEABLE_ITEM = (state, world, pos, player, hand, stack) -> {
		Item item = stack.getItem();
		if ((item instanceof DyeableItem dyeableItem)) {
			if (!world.isClient) {
				dyeableItem.setColor(stack, 0xFFFFFF);
				player.incrementStat(Stats.CLEAN_ARMOR);
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	};
	public static final CauldronBehavior MILKIFY_SHULKER_BOX = (state, world, pos, player, hand, stack) -> {
		Block block = Block.getBlockFromItem(stack.getItem());
		if ((block instanceof ShulkerBoxBlock)) {
			if (!world.isClient) {
				ItemStack itemStack = new ItemStack(Blocks.WHITE_SHULKER_BOX);
				if (stack.hasTag()) {
					itemStack.setTag(stack.getTag().copy());
				}
				
				player.setStackInHand(hand, itemStack);
				player.incrementStat(Stats.CLEAN_SHULKER_BOX);
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	};
	public static final CauldronBehavior MILKIFY_BANNER = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient()) {
			ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
			if (!player.getAbilities().creativeMode) {
				stack.decrement(1);
			}
			
			if (stack.isEmpty()) {
				player.setStackInHand(hand, itemStack);
			} else if (player.getInventory().insertStack(itemStack)) {
				player.playerScreenHandler.syncState();
			} else {
				player.dropItem(itemStack, false);
			}
			
			player.incrementStat(Stats.CLEAN_BANNER);
			LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
		}
		return ActionResult.success(world.isClient());
	};
	
	public MilkCauldron(Settings settings) {
		super(settings, precipitation -> false, ((Supplier<Map<Item, CauldronBehavior>>) () -> {
			// dyeables
			for (Field field : Items.class.getDeclaredFields()) {
				try {
					Item item = (Item) field.get(null);
					if (item instanceof DyeableItem) {
						MilkCauldron.MILK_CAULDRON_BEHAVIOR.put(item, MilkCauldron.MILKIFY_DYEABLE_ITEM);
					} else if (item instanceof BannerItem) {
						MilkCauldron.MILK_CAULDRON_BEHAVIOR.put(item, MilkCauldron.MILKIFY_BANNER);
					} else if (item instanceof BlockItem blockItem) {
						if (blockItem.getBlock() instanceof ShulkerBoxBlock) {
							MilkCauldron.MILK_CAULDRON_BEHAVIOR.put(item, MilkCauldron.MILKIFY_SHULKER_BOX);
						}
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			
			MILK_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_FROM_BUCKET);
			MILK_CAULDRON_BEHAVIOR.put(MilkPlus.MILK_BOTTLE, FILL_FROM_BOTTLE);
			
			MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, EMPTY_TO_BUCKET);
			MILK_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, EMPTY_TO_BOTTLE);
			
			return MILK_CAULDRON_BEHAVIOR;
		}).get());
	}
}
