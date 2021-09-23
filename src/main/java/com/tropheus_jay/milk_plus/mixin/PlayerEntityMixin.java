package com.tropheus_jay.milk_plus.mixin;

import com.tropheus_jay.milk_plus.MilkPlus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "interact",at = @At("HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        //owo
        if (entity instanceof PlayerEntity player) {
            PlayerEntity self = (PlayerEntity) (Object) this;
            ItemStack item = self.getStackInHand(hand);
            if (!self.isSpectator() && item.isOf(Items.GLASS_BOTTLE)) {
                //sound
                self.playSound(SoundEvents.ENTITY_GOAT_MILK, 1.0F, 1.0F);

                //get milk item
                ItemStack milk = MilkPlus.MILK_BOTTLE.getDefaultStack();
                milk.setCustomName(new LiteralText("").setStyle(Style.EMPTY.withItalic(false)).formatted(Formatting.YELLOW).append(player.getName()).append(" ").append(milk.getName()));

                //exchange item
                ItemStack itemStack2 = ItemUsage.exchangeStack(item, self, milk, false);
                self.setStackInHand(hand, itemStack2);

                //return
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
