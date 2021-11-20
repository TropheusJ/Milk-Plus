package io.github.tropheusj.mixin;

import io.github.tropheusj.MilkPlus;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class BrewingStandScreenHandlerPotionSlotMixin {
	@Inject(method = "matches", at = @At("HEAD"), cancellable = true)
	private static void matches(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isOf(MilkPlus.SPLASH_MILK_BOTTLE)  || stack.isOf(MilkPlus.LINGERING_MILK_BOTTLE) || stack.isOf(MilkPlus.MILK_BOTTLE)) {
			cir.setReturnValue(true);
		}
	}
}
