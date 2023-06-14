package io.github.tropheusj.mixin;

import io.github.tropheusj.MilkPlus;
import io.github.tropheusj.milk.Milk;
import io.github.tropheusj.milk.MilkFluid;
import io.github.tropheusj.milk.potion.bottle.MilkBottle;
import io.github.tropheusj.milk_holders.potion.arrow.ArrowEntityExtensions;
import io.github.tropheusj.milk_holders.potion.arrow.MilkTippedArrowItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity implements ArrowEntityExtensions {
	protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Shadow
	protected abstract void setColor(int color);

	@Unique
	private boolean milk = false;
	
	@Inject(at = @At("HEAD"), method = "initFromStack")
	public void milk_plus$initFromStack(ItemStack stack, CallbackInfo ci) {
		if (stack.getItem() instanceof MilkTippedArrowItem) {
			setMilk(true);
			setColor(0xFFFFFF);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "onHit")
	protected void milk_plus$onHit(LivingEntity target, CallbackInfo ci) {
		if (isMilk()) {
			boolean skeleton = target.getType().isIn(MilkPlus.SKELETONS);
			if (target.getStatusEffects().size() > 0) {
				List<StatusEffect> remove = new ArrayList<>();
				Collection<StatusEffectInstance> effects = target.getStatusEffects();
				for (StatusEffectInstance effect : effects) {
					// non-skeleton: remove any
					// skeleton: only bad
					StatusEffect type = effect.getEffectType();
					boolean shouldRemove = !skeleton || !type.isBeneficial();
					if (shouldRemove) {
						remove.add(type);
						if (!skeleton) break; // only remove 1 at most for non-skeletons
					}
				}
				remove.forEach(target::removeStatusEffect);
			}
			if (skeleton) {
				// give skeletons as many effects as possible
				// Calcium: It's Good For Your Bones™
				for (StatusEffect effect : Registries.STATUS_EFFECT) {
					if (MilkPlus.arrowShouldApplyEffect(target, effect)) {
						if (effect.isInstant()) {
							effect.applyInstantEffect(null, null, target, 1, 1);
						} else {
																							// 30 seconds at 20 tps
							StatusEffectInstance instance = new StatusEffectInstance(effect, 20 * 30, 1);
							target.addStatusEffect(instance);
						}
					}
				}
				if (getOwner() instanceof ServerPlayerEntity player) {
					MilkPlus.CALCIUM_SKELETON_CRITERION.trigger(player);
					getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.HOSTILE, 1, 1);
				}
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void milk_plus$tick(CallbackInfo ci) {
		if (!isMilk()) {
			Direction directionToCheck = getHorizontalFacing();
			for (int i = 0; i <= 3; i++) {
				if (getWorld().getFluidState(getBlockPos().offset(directionToCheck, i)).getFluid() instanceof MilkFluid) {
					setMilk(true);
					setColor(0xFFFFFF);
				}
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "asItemStack", cancellable = true)
	private void milk_plus$milkArrowItems(CallbackInfoReturnable<ItemStack> cir) {
		if (isMilk()) {
			cir.setReturnValue(MilkPlus.MILK_ARROW.getDefaultStack());
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	public void milk_plus$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean("Milk", isMilk());
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	public void milk_plus$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		setMilk(nbt.getBoolean("Milk"));
	}
	
	@Override
	public void setMilk(boolean value) {
		milk = value;
	}
	
	@Override
	public boolean isMilk() {
		return milk;
	}
}
