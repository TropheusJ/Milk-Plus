package com.tropheus_jay.milk_plus.mixin;

import com.tropheus_jay.milk_plus.MixinHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.Items.MILK_BUCKET;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/item/Item;getDefaultStack()Lnet/minecraft/item/ItemStack;", cancellable = true)
	public void getDefaultStack(CallbackInfoReturnable<ItemStack> cir) {
		if (MixinHelper.<Item>cast(this).equals(Items.MILK_BUCKET)) {
			cir.setReturnValue(new ItemStack(Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET))));
		}
	}
}
