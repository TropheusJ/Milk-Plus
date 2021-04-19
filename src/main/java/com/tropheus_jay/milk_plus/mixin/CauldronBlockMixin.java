package com.tropheus_jay.milk_plus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.tropheus_jay.milk_plus.MilkPlus.MILK_BOTTLE;
import static com.tropheus_jay.milk_plus.MilkPlus.MILK_CAULDRON;
import static net.minecraft.item.Items.MILK_BUCKET;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin extends Block {
	@Shadow @Final public static IntProperty LEVEL;
	
	private CauldronBlockMixin(Settings settings) {super(settings);}
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift = At.Shift.AFTER),
			method = "Lnet/minecraft/block/CauldronBlock;onUse(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", cancellable = true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack milk_plus$itemStack = player.getStackInHand(hand);
		if (milk_plus$itemStack.getItem() == Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET)) && world.canSetBlock(pos) && state.get(LEVEL) == 0) {
			world.setBlockState(pos, MILK_CAULDRON.getDefaultState());
			world.getBlockState(pos).getBlock().onUse(MILK_CAULDRON.getDefaultState(), world, pos, player, hand, hit);
			cir.setReturnValue(ActionResult.SUCCESS);
		} else if (milk_plus$itemStack.getItem() == MILK_BOTTLE && world.canSetBlock(pos) && state.get(LEVEL) == 0) {
			world.setBlockState(pos, MILK_CAULDRON.getDefaultState());
			world.getBlockState(pos).getBlock().onUse(MILK_CAULDRON.getDefaultState(), world, pos, player, hand, hit);
			cir.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
