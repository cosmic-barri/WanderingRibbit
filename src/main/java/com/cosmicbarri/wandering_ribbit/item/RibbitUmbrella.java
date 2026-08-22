package com.cosmicbarri.wandering_ribbit.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RibbitUmbrella extends Item {
    public RibbitUmbrella() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, level, entity, slot, selected);
        if (level.isClientSide) return;
        if (entity instanceof Player player)
            if (player.getMainHandItem().getItem() instanceof RibbitUmbrella || player.getOffhandItem().getItem() instanceof RibbitUmbrella)
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false));
    }
}
