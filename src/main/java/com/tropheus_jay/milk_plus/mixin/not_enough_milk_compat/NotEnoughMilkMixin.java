package com.tropheus_jay.milk_plus.mixin.not_enough_milk_compat;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suitedllama.notenoughmilk.NotEnoughMilk;

@Pseudo
@Mixin(value = NotEnoughMilk.class, remap = false)
public abstract class NotEnoughMilkMixin {
	private static final String MOD_ID = "notenoughmilk";
//	@Shadow @Final @Mutable public static final Item BAT_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item CAT_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item CHICKEN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item FISH_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item DONKEY_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item FOX_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SQUID_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PIG_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SNOW_GOLEM_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item HORSE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item MOOSHROOM_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SHULKER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item CREEPER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item IRON_GOLEM_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PARROT_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item BLAZE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item GHAST_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ZOMBIE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SPIDER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item CAVE_SPIDER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item WITCH_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SLIME_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item MAGMA_CUBE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SKELETON_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item STRAY_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item RABBIT_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item BEE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item DOLPHIN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item WITHER_SKELETON_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ENDERMAN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item POLAR_BEAR_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item MULE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item TURTLE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item WOLF_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ZOMBIFIED_PIGLIN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ZOGLIN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SHEEP_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PANDA_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item VILLAGER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item OCELOT_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item STRIDER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item VINDICATOR_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PILLAGER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PHANTOM_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item EVOKER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item RAVAGER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item LLAMA_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item HOGLIN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item PIGLIN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item DROWNED_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item SILVERFISH_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ENDERMITE_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item VEX_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item GUARDIAN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ELDER_GUARDIAN_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item WITHER_MILK_BUCKET;
//	@Shadow @Final @Mutable public static final Item ENDER_DRAGON_MILK_BUCKET;
	
//	@Inject(at = @At("HEAD"), method = "Lsuitedllama/notenoughmilk/NotEnoughMilk;onInitialize()V")
//	public void onInitialize(CallbackInfo ci) {
//
//	}
	
}
