package com.tropheus_jay.milk_plus.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;

import static com.tropheus_jay.milk_plus.MilkPlus.*;
import static net.minecraft.item.Items.MILK_BUCKET;

public abstract class MilkFluid extends AbstractFluid {
	@Override
	public Fluid getStill() {
		return STILL_MILK;
	}
	
	@Override
	public Fluid getFlowing() {
		return FLOWING_MILK;
	}
	
	@Override
	public Item getBucketItem() {
		return Registry.ITEM.get(Registry.ITEM.getRawId(MILK_BUCKET));
	}

	@Override
	protected int getFlowSpeed(WorldView worldView) {
		return 2;
	}
	
	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		// method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
		return MILK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
	}
	
	public static class Flowing extends MilkFluid {
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
	
	public static class Still extends MilkFluid {
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
