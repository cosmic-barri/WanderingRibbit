package com.cosmicbarri.wandering_ribbit.item;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class RibbitMap extends Item {
    public RibbitMap() {
        super(new Item.Properties());
    }
    public void createMap(Player player) {
        if (player.level() instanceof ServerLevel level) {
            ResourceKey<Structure> structureKey = ResourceKey.create(
                    Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath("ribbits", "ribbit_village")
            );
            Registry<Structure> structureRegistry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
            Holder<Structure> holder = structureRegistry.getHolderOrThrow(structureKey);
            HolderSet<Structure> holderSet = HolderSet.direct(holder);
            var result = level.getChunkSource().getGenerator().findNearestMapStructure(
                    level, holderSet, player.blockPosition(), 100, false
            );
            if (result != null) {
                ItemStack map = MapItem.create(level, result.getFirst().getX(), result.getFirst().getZ(), (byte) 2, true, true);
                MapItemSavedData.addTargetDecoration(map, result.getFirst(), "+", MapDecoration.Type.TARGET_X);
                map.setHoverName(Component.translatable("item.wandering_ribbit.ribbit_map_item"));
                player.getMainHandItem().shrink(1);
                player.addItem(map);
            } else {
                player.displayClientMessage(Component.translatable("item.wandering_ribbit.ribbit_map_error"), true);
            }
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        createMap(player);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
