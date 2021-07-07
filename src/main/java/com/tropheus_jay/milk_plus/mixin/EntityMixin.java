package com.tropheus_jay.milk_plus.mixin;

import com.tropheus_jay.milk_plus.MilkPlus;
import com.tropheus_jay.milk_plus.MixinHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "Lnet/minecraft/entity/Entity;updateMovementInFluid(Lnet/minecraft/tag/Tag;D)Z",
			at = @At("HEAD"))
	public void updateMovementInFluid(Tag<Fluid> tag, double d, CallbackInfoReturnable<Boolean> cir) {
		if (MixinHelper.cast(this) instanceof PlayerEntity) {
			FluidState fluidState = MixinHelper.<Entity>cast(this).world.getFluidState(MixinHelper.<Entity>cast(this).getBlockPos());
			if (fluidState.getFluid() == MilkPlus.STILL_MILK || fluidState.getFluid() == MilkPlus.FLOWING_MILK) {
				MixinHelper.<PlayerEntity>cast(this).clearStatusEffects();
			}
		}
	}
}
