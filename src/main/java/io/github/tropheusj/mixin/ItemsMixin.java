package io.github.tropheusj.mixin;

import io.github.tropheusj.fluid.MilkFluid;
import io.github.tropheusj.milk_holders.PlaceableMilkBucket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.Items.BUCKET;

@Mixin(Items.class)
public abstract class ItemsMixin {
	@Shadow
	private static Item register(Identifier id, Item item) {
		return null;
	}
	
	@Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", cancellable = true)
	private static void milk_plus$register(String id, Item item, CallbackInfoReturnable<Item> cir) {
		if (id.equals("milk_bucket")) {
			cir.setReturnValue(register(new Identifier(id),
					new PlaceableMilkBucket(new MilkFluid.Still(), new Item.Settings().recipeRemainder(BUCKET).maxCount(1).group(ItemGroup.MISC))));
		}
	}
}
