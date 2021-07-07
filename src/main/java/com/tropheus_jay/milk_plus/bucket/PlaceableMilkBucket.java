package com.tropheus_jay.milk_plus.bucket;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import static net.minecraft.item.Items.BUCKET;
import static net.minecraft.item.Items.MILK_BUCKET;

public class PlaceableMilkBucket extends BucketItem {
	boolean shouldDrink;
	
	public PlaceableMilkBucket(Fluid fluid, Settings settings) {
		super(fluid, settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		HitResult hit = raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (hit instanceof BlockHitResult) {
			BlockState targetState = world.getBlockState(((BlockHitResult) hit).getBlockPos());
			if (targetState.getBlock() instanceof CauldronBlock) {
				user.setStackInHand(hand, new ItemStack(BUCKET));
				return TypedActionResult.success(this.getDefaultStack(), true);
			}
		}
		
		shouldDrink = user.isSneaking();
		if (shouldDrink) {
			return ItemUsage.consumeHeldItem(world, user, hand);
		} else {
			return super.use(world, user, hand);
		}
	}
	
	@Override
	public Item asItem() {
		return this;
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (shouldDrink) {
			if (user instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user;
				Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
				serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
			}
			
			if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
				stack.decrement(1);
			}
			
			if (!world.isClient) {
				user.clearStatusEffects();
			}
			
			return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
		} else {
			return stack;
		}
	}
	
	@Override
	public ItemStack getDefaultStack() {
		return new ItemStack(Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET)));
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
}
