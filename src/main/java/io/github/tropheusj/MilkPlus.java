package io.github.tropheusj;

import io.github.tropheusj.milk.Milk;
import io.github.tropheusj.milk.MilkCauldron;
import io.github.tropheusj.milk_holders.MilkBowlItem;
import io.github.tropheusj.milk_holders.potion.arrow.ArrowEntityExtensions;
import io.github.tropheusj.milk_holders.potion.arrow.MilkTippedArrowItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import javax.annotation.Nullable;

import static io.github.tropheusj.milk.Milk.STILL_MILK;
import static net.minecraft.item.Items.BOWL;

public class MilkPlus implements ModInitializer {
	public static final String ID = "milk_plus";
	public static Item MILK_ARROW;
	public static Item MILK_BOWL;

	// entities which will be healed by milk arrows.
	public static final TagKey<EntityType<?>> SKELETONS = TagKey.of(Registry.ENTITY_TYPE_KEY, id("skeletons"));

	public static final CalciumSkeletonCriterion CALCIUM_SKELETON_CRITERION = CriterionRegistry.register(new CalciumSkeletonCriterion());
	
	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		Milk.enableMilkFluid();
		Milk.enableMilkPlacing();
		Milk.enableAllMilkBottles();
		Milk.enableCauldron();
		MILK_ARROW = Registry.register(Registry.ITEM, id("milk_arrow"),
				new MilkTippedArrowItem((new FabricItemSettings().group(ItemGroup.COMBAT))));
		MILK_BOWL = Registry.register(Registry.ITEM, id("milk_bowl"),
				new MilkBowlItem(new FabricItemSettings().maxCount(1).group(ItemGroup.FOOD).food(new FoodComponent.Builder().alwaysEdible().build())));
		
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
