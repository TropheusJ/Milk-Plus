package com.tropheus_jay.milk_plus.mixin;

import com.tropheus_jay.milk_plus.fluid.MilkFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Shadow @Final private Fluid fluid;

    @Redirect(method = "placeFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/Fluid;isIn(Lnet/minecraft/tag/Tag;)Z"))
    public boolean placeFluid(Fluid fluid, Tag<Fluid> tag) {
        if (fluid instanceof MilkFluid) return true;
        return this.fluid.isIn(FluidTags.WATER);
    }
}
