package com.tropheus_jay.milk_plus.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

import static com.tropheus_jay.milk_plus.MilkPlus.*;
import static net.minecraft.item.Items.MILK_BUCKET;

public abstract class GenericFluid extends AbstractFluid {
	Item bucketItem = MILK_BUCKET;
	Fluid still = STILL_MILK;
	Fluid flowing = FLOWING_MILK;
	Block block = MILK;
	
	@Override
	public Fluid getStill() {
		return still;
	}
	
	@Override
	public Fluid getFlowing() {
		return flowing;
	}
	
	@Override
	public Item getBucketItem() {
		return bucketItem;
	}
	
	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		// method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
		return block.getDefaultState().with(Properties.LEVEL_15, method_15741(fluidState));
	}
	
	public static class Flowing extends GenericFluid {
		
		public Flowing() {}
		
		public void updateReferences(int index) {
			bucketItem = ITEMS[index];
			still = STILL_FLUIDS[index];
			flowing = FLOWING_FLUIDS[index];
			block = FLUID_BLOCKS[index];
		}
		
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}
		
		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}
		
		@Override
		public boolean isStill(FluidState fluidState) {
			return false;
		}
	}
	
	public static class Still extends GenericFluid {
		
		public Still() {}
		
		public void updateReferences(int index) {
			bucketItem = ITEMS[index];
			still = STILL_FLUIDS[index];
			flowing = FLOWING_FLUIDS[index];
			block = FLUID_BLOCKS[index];
		}
		
		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}
		
		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}
}
