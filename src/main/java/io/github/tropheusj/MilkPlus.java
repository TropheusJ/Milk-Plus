package io.github.tropheusj;

import io.github.tropheusj.milk.Milk;
import io.github.tropheusj.milk.MilkCauldron;
import io.github.tropheusj.milk_holders.MilkBowlItem;
import io.github.tropheusj.milk_holders.potion.arrow.MilkTippedArrowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import static io.github.tropheusj.milk.Milk.STILL_MILK;
import static net.minecraft.item.Items.BOWL;

import java.util.List;

public class MilkPlus implements ModInitializer {
	public static final String ID = "milk_plus";
	public static Item MILK_ARROW;
	public static Item MILK_BOWL;

	// entities which will be healed by milk arrows.
	public static final TagKey<EntityType<?>> SKELETONS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("c", "skeletons"));

	public static final CalciumSkeletonCriterion CALCIUM_SKELETON_CRITERION = Criteria.register(new CalciumSkeletonCriterion());
	
	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Milk.enableMilkFluid();
		Milk.enableMilkPlacing();
		Milk.enableAllMilkBottles();
		Milk.enableCauldron();

		MILK_ARROW = Registry.register(Registries.ITEM, id("milk_arrow"),
				new MilkTippedArrowItem((new FabricItemSettings())));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addAfter(Items.SPECTRAL_ARROW, MILK_ARROW));

		MILK_BOWL = Registry.register(Registries.ITEM, id("milk_bowl"),
				new MilkBowlItem(new FabricItemSettings().maxCount(1).food(new FoodComponent.Builder().alwaysEdible().build())));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addBefore(Items.MILK_BUCKET, MILK_BOWL));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.addAfter(Items.MILK_BUCKET, MILK_BOWL));

		MilkCauldron.addInputToCauldronExchange(MILK_BOWL.getDefaultStack(), BOWL.getDefaultStack(), true);
		MilkCauldron.addOutputToItemExchange(BOWL.getDefaultStack(), MILK_BOWL.getDefaultStack(), true);
		DispenserBlock.registerBehavior(MILK_ARROW, new ProjectileDispenserBehavior() {
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				ArrowEntity arrowEntity = new ArrowEntity(world, position.getX(), position.getY(), position.getZ());
				arrowEntity.initFromStack(stack);
				arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
				return arrowEntity;
			}
		});
		
		initFluidApi();
	}
	
	@SuppressWarnings("UnstableApiUsage")
	public void initFluidApi() {
		FluidStorage.combinedItemApiProvider(MILK_BOWL).register(context ->
				new FullItemFluidStorage(context, bowl -> ItemVariant.of(BOWL), FluidVariant.of(STILL_MILK), FluidConstants.BOTTLE)
		);
		FluidStorage.combinedItemApiProvider(BOWL).register(context ->
				new EmptyItemFluidStorage(context, bucket -> ItemVariant.of(MILK_BOWL), STILL_MILK, FluidConstants.BOTTLE)
		);
	}

	public static boolean arrowShouldApplyEffect(LivingEntity target, StatusEffect effect) {
		// no invisibility, levitation, or wither
		if (effect == StatusEffects.INVISIBILITY || effect == StatusEffects.LEVITATION || effect == StatusEffects.WITHER)
			return false;
		boolean undead = target.isUndead();
		if (effect == StatusEffects.POISON || effect == StatusEffects.INSTANT_DAMAGE)
			return undead;
		else if (effect == StatusEffects.REGENERATION || effect == StatusEffects.INSTANT_HEALTH)
			return !undead;
		return true;
	}

}
