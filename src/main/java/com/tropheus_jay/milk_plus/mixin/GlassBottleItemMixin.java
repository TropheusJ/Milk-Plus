package com.tropheus_jay.milk_plus.mixin;

import com.tropheus_jay.milk_plus.MilkPlus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
	private GlassBottleItemMixin(Settings settings) {
		super(settings);
	}
	
	@Shadow
	protected abstract ItemStack fill(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2);
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
			ordinal = 1, shift = At.Shift.AFTER), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		HitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
		if (world.getFluidState(blockPos).getFluid() == MilkPlus.STILL_MILK || world.getFluidState(blockPos).getFluid() == MilkPlus.FLOWING_MILK) {
			cir.setReturnValue(TypedActionResult.success(fill(user.getStackInHand(hand), user, new ItemStack(MilkPlus.MILK_BOTTLE))));
		}
	}
}
