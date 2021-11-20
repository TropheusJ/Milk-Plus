package io.github.tropheusj.mixin;

import io.github.tropheusj.fluid.MilkFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"), method = "render")
	private boolean redirectOverlaySprite(FluidState fluidState, Tag<Fluid> tag) {
		if (fluidState.getFluid() instanceof MilkFluid) return true;
		return fluidState.isIn(FluidTags.LAVA);
	}
}
