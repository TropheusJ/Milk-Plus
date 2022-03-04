package io.github.tropheusj.mixin;

import io.github.tropheusj.MilkPlus;
import io.github.tropheusj.milk.Milk;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Shadow
	protected static BlockHitResult raycast(World world, PlayerEntity player, FluidHandling fluidHandling) {
		throw new RuntimeException("mixin failed!");
	}
	
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void milk_plus$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (Items.BOWL.equals(this)) {
			BlockHitResult hitResult = raycast(world, user, FluidHandling.SOURCE_ONLY);
			ItemStack itemStack = user.getStackInHand(hand);
			if (hitResult.getType() == Type.BLOCK) {
				BlockPos blockPos = hitResult.getBlockPos();
				if (world.canPlayerModifyAt(user, blockPos)) {
					if (Milk.isMilk(world.getFluidState(blockPos))) {
						world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
						world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
						cir.setReturnValue(TypedActionResult.success(
								this.milk_plus$fill(itemStack, user, MilkPlus.MILK_BOWL.getDefaultStack()), world.isClient()
						));
					}
				}
			}
		}
	}

	protected ItemStack milk_plus$fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
		player.incrementStat(Stats.USED.getOrCreateStat((Item) (Object) this));
		return ItemUsage.exchangeStack(stack, player, outputStack);
	}
}
