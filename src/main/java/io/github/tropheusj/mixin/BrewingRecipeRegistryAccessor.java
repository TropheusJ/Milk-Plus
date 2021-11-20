package io.github.tropheusj.mixin;

import net.minecraft.item.Item;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryAccessor {
	@Invoker("registerPotionType")
	static void invokeRegisterPotionType(Item item) {
		throw new RuntimeException("Mixin application failed!");
	}
	
	@Invoker("registerItemRecipe")
	static void invokeRegisterItemRecipe(Item input, Item ingredient, Item output) {
		throw new RuntimeException("Mixin application failed!");
	}
}
