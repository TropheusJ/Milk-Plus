package com.tropheus_jay.milk_plus.cauldron;

import com.tropheus_jay.milk_plus.MilkPlus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static net.minecraft.item.Items.*;
import static net.minecraft.potion.Potions.WATER;

public class MilkCauldron extends CauldronBlock {
	public MilkCauldron(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(LEVEL);
	}
	
	
	@Environment(EnvType.CLIENT)
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(Blocks.CAULDRON);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack heldStack = player.getStackInHand(hand);
		if (heldStack.isEmpty()) {
			return ActionResult.PASS;
		} else {
			int level = state.get(LEVEL);
			Item item = heldStack.getItem();
			if ((item == WATER_BUCKET || (item == Items.POTION && PotionUtil.getPotion(heldStack) == Potions.WATER)) && level == 0) {
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
				return ActionResult.SUCCESS;
			}
			if (item == Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET))) {
				if (level < 3 && !world.isClient) {
					if (!player.abilities.creativeMode) {
						player.setStackInHand(hand, new ItemStack(Items.BUCKET));
					}
					
					player.incrementStat(Stats.FILL_CAULDRON);
					this.setLevel(world, pos, state, 3);
					world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				
				return ActionResult.success(world.isClient);
			} else if (item == Items.BUCKET) {
				if (level == 3 && !world.isClient) {
					if (!player.abilities.creativeMode) {
						heldStack.decrement(1);
						if (heldStack.isEmpty()) {
							player.setStackInHand(hand, new ItemStack(Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET))));
						} else if (!player.inventory.insertStack(new ItemStack(Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET))))) {
							player.dropItem(new ItemStack(Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET))), false);
						}
					}
					
					player.incrementStat(Stats.USE_CAULDRON);
					this.setLevel(world, pos, state, 0);
					world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				
				return ActionResult.success(world.isClient);
			} else {
				ItemStack newStack;
				if (item == Items.GLASS_BOTTLE) {
					if (level > 0 && !world.isClient) {
						if (!player.abilities.creativeMode) {
							newStack = new ItemStack(MilkPlus.MILK_BOTTLE);
							player.incrementStat(Stats.USE_CAULDRON);
							heldStack.decrement(1);
							if (heldStack.isEmpty()) {
								player.setStackInHand(hand, newStack);
							} else if (!player.inventory.insertStack(newStack)) {
								player.dropItem(newStack, false);
							} else if (player instanceof ServerPlayerEntity) {
								((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
							}
						}
						
						world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
						this.setLevel(world, pos, state, level - 1);
					}
					
					return ActionResult.success(world.isClient);
				} else if (item == MilkPlus.MILK_BOTTLE) {
					if (level < 3 && !world.isClient) {
						if (!player.abilities.creativeMode) {
							newStack = new ItemStack(Items.GLASS_BOTTLE);
							player.incrementStat(Stats.USE_CAULDRON);
							player.setStackInHand(hand, newStack);
							if (player instanceof ServerPlayerEntity) {
								((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
							}
						}
						
						world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
						this.setLevel(world, pos, state, level + 1);
					}
					
					return ActionResult.success(world.isClient);
				} else {
					if (level > 0 && item instanceof DyeableItem) {
						DyeableItem dyeableItem = (DyeableItem)item;
						if (!world.isClient) {
							dyeableItem.setColor(heldStack, 0xffffff);
							this.setLevel(world, pos, state, level - 1);
							return ActionResult.SUCCESS;
						}
					}
					
					if (level > 0 && item instanceof BannerItem) {
						if (BannerBlockEntity.getPatternCount(heldStack) > 0 && !world.isClient) {
							newStack = new ItemStack(Blocks.WHITE_BANNER);
							BannerBlockEntity.loadFromItemStack(newStack);
							if (!player.abilities.creativeMode) {
								heldStack.decrement(1);
								this.setLevel(world, pos, state, level - 1);
							}
							
							if (heldStack.isEmpty()) {
								player.setStackInHand(hand, newStack);
							} else if (!player.inventory.insertStack(newStack)) {
								player.dropItem(newStack, false);
							} else if (player instanceof ServerPlayerEntity) {
								((ServerPlayerEntity)player).refreshScreenHandler(player.playerScreenHandler);
							}
						}
						
						return ActionResult.success(world.isClient);
					} else if (level > 0 && item instanceof BlockItem) {
						Block block = ((BlockItem)item).getBlock();
						if (block instanceof ShulkerBoxBlock && !world.isClient()) {
							ItemStack newShulkerStack = new ItemStack(Blocks.WHITE_SHULKER_BOX, 1);
							if (heldStack.hasTag()) {
								newShulkerStack.setTag(heldStack.getTag().copy());
							}
							
							player.setStackInHand(hand, newShulkerStack);
							this.setLevel(world, pos, state, level - 1);
							return ActionResult.SUCCESS;
						} else {
							return ActionResult.CONSUME;
						}
					} else {
						return ActionResult.PASS;
					}
				}
			}
		}
	}
}
