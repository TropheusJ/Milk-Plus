package com.tropheus_jay.milk_plus.milk_holders;

import com.tropheus_jay.milk_plus.MilkPlus;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;

public class MilkBowlItem extends MushroomStewItem {
	public MilkBowlItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult hit = raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hit.getBlockPos();
			if (world.getBlockState(pos).isOf(Blocks.CAULDRON)) {
				user.setStackInHand(hand, ItemUsage.exchangeStack(user.getStackInHand(hand), user, new ItemStack(MilkPlus.MILK_BOWL)));
				world.setBlockState(pos, MilkPlus.MILK_CAULDRON.getDefaultState().with(LEVEL, 1));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				return TypedActionResult.success(Items.BOWL.getDefaultStack(), true);
			}
		}
		
		return super.use(world, user, hand);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack result = super.finishUsing(stack, world, user);
		if (user.getStatusEffects().size() > 0) {
			user.clearStatusEffects();
		}
		return result;
	}
}
