package com.tropheus_jay.milk_plus.mixin.not_enough_milk_compat;

import com.tropheus_jay.milk_plus.MilkPlus;
import com.tropheus_jay.milk_plus.NotEnoughMilkItemAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.milks.*;

@Pseudo
@Mixin(value = {BatMilkItem.class, BeeMilkItem.class, BlazeMilkItem.class, CatMilkItem.class, CaveSpiderMilkItem.class,
		ChickenMilkItem.class, CreeperMilkItem.class, DolphinMilkItem.class, DonkeyMilkItem.class, DrownedMilkItem.class,
		ElderGuardianMilkItem.class, EnderDragonMilkItem.class, EndermanMilkItem.class, EndermiteMilkItem.class,
		EvokerMilkItem.class, FishMilkItem.class, FoxMilkItem.class, GhastMilkItem.class, GuardianMilkItem.class,
		HoglinMilkItem.class, HorseMilkItem.class, IronGolemMilkItem.class, LlamaMilkItem.class, MagmaCubeMilkItem.class,
		MooshroomMilkItem.class, MuleMilkItem.class, PandaMilkItem.class, ParrotMilkItem.class, PhantomMilkItem.class,
		PiglinMilkItem.class, PigMilkItem.class, PillagerMilkItem.class, PlayerMilkItem.class, PolarBearMilkItem.class,
		RabbitMilkItem.class, RavagerMilkItem.class, SheepMilkItem.class, ShulkerMilkItem.class, SilverfishMilkItem.class,
		SkeletonMilkItem.class, SlimeMilkItem.class, SnowGolemMilkItem.class, SpiderMilkItem.class, SquidMilkItem.class,
		StrayMilkItem.class, StriderMilkItem.class, TurtleMilkItem.class, VexMilkItem.class, VillagerMilkItem.class,
		VindicatorMilkItem.class, WitchMilkItem.class, WitherMilkItem.class, WitherSkeletonMilkItem.class,
		WolfMilkItem.class, ZombieMilkItem.class, ZombifiedMilkItem.class}, remap = false)
public abstract class NotEnoughMilkItemMixin extends Item implements NotEnoughMilkItemAccessor {
	public NotEnoughMilkItemMixin(Settings settings) {
		super(settings);
	}
	
	Fluid fluid;
	
	@Inject(at = @At("TAIL"), method = "<init>")
	public void NotEnoughMilkItem(Settings settings, CallbackInfo ci) {
		MilkPlus.addToClassList(this);
	}
	
	@Override
	public void refreshFluidReference() {
		fluid = MilkPlus.getFluidFromClass(this);
	}
	
	@Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		refreshFluidReference();
		if (user.isSneaking()) {
			cir.setReturnValue(ItemUsage.consumeHeldItem(world, user, hand));
		} else {
			cir.setReturnValue(tryPlaceFluid(world, user, hand));
		}
	}
	
	public TypedActionResult<ItemStack> tryPlaceFluid(World world, PlayerEntity user, Hand hand) {
		ItemStack heldStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (hitResult.getType() == HitResult.Type.MISS || hitResult.getType() != HitResult.Type.BLOCK) {
			return TypedActionResult.pass(heldStack);
		} else {
			BlockHitResult blockHitResult = (BlockHitResult) hitResult;
			BlockPos hitPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getSide();
			BlockPos offsetPos = hitPos.offset(direction);
			if (!world.canPlayerModifyAt(user, hitPos) && user.canPlaceOn(offsetPos, direction, heldStack)) {
				return TypedActionResult.fail(heldStack);
			}
			if (placeFluid(user, world, offsetPos, blockHitResult)) {
				if (user instanceof ServerPlayerEntity) {
					Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, offsetPos, heldStack);
				}
				
				user.incrementStat(Stats.USED.getOrCreateStat(this));
				return TypedActionResult.success(!user.abilities.creativeMode ? new ItemStack(Items.BUCKET) : heldStack, world.isClient());
			} else {
				return TypedActionResult.fail(heldStack);
			}
		}
	}
	
	public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult blockHitResult) {
		if (!(fluid instanceof FlowableFluid)) {
			return false;
		} else {
			BlockState targetState = world.getBlockState(pos);
			Block block = targetState.getBlock();
			Material material = targetState.getMaterial();
			boolean canPlace = targetState.canBucketPlace(fluid);
			if (world.getDimension().isUltrawarm() && fluid.isIn(FluidTags.WATER)) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
				for(int l = 0; l < 8; ++l) {
					world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
				}
				return true;
				
			} else {
				if (!world.isClient && canPlace && !material.isLiquid()) {
					world.breakBlock(pos, true);
				}
				
				if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !targetState.getFluidState().isStill()) {
					return false;
				} else {
					world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return true;
				}
			}
		}
	}
}
