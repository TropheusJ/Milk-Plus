package io.github.tropheusj.mixin;

import io.github.tropheusj.fluid.MilkFluid;
import io.github.tropheusj.milk_holders.potion.arrow.ArrowEntityExtensions;
import io.github.tropheusj.milk_holders.potion.arrow.MilkTippedArrowItem;
import io.github.tropheusj.milk_holders.potion.bottle.MilkBottle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
	public void initFromStack(ItemStack stack, CallbackInfo ci) {
		if (stack.getItem() instanceof MilkTippedArrowItem) {
			setMilk(true);
			setColor(0xFFFFFF);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "onHit")
	protected void onHit(LivingEntity target, CallbackInfo ci) {
		if (isMilk()) {
			MilkBottle.tryRemoveRandomEffect(target);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo ci) {
		if (!isMilk()) {
			Direction directionToCheck = getHorizontalFacing();
			for (int i = 0; i <= 3; i++) {
				if (world.getFluidState(getBlockPos().offset(directionToCheck, i)).getFluid() instanceof MilkFluid) {
					setMilk(true);
					setColor(0xFFFFFF);
				}
			}
		}
	}
	
	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean("Milk", isMilk());
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
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
