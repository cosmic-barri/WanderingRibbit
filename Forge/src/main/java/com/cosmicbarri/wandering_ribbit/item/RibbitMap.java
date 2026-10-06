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
import org.jetbrains.annotations.NotNull;

public class RibbitMap extends Item {
    public RibbitMap() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (level instanceof ServerLevel sl) {
            ResourceKey<Structure> structureKey = ResourceKey.create(
                    Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath("ribbits", "ribbit_village")
            );
            Registry<Structure> structureRegistry = sl.registryAccess().registryOrThrow(Registries.STRUCTURE);
            Holder<Structure> holder = structureRegistry.getHolderOrThrow(structureKey);
            HolderSet<Structure> holderSet = HolderSet.direct(holder);
            var result = sl.getChunkSource().getGenerator().findNearestMapStructure(
                    sl, holderSet, player.blockPosition(), 100, false
            );
            if (result != null) {
                ItemStack map = MapItem.create(level, result.getFirst().getX(), result.getFirst().getZ(), (byte) 2, true, true);
                MapItem.renderBiomePreviewMap(sl, map);
                MapItemSavedData.addTargetDecoration(map, result.getFirst(), "+", MapDecoration.Type.TARGET_X);
                map.setHoverName(Component.translatable("item.wandering_ribbit.ribbit_map_item"));
                player.getItemInHand(hand).shrink(1);
                player.addItem(map);
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
            } else {
                player.displayClientMessage(Component.translatable("item.wandering_ribbit.ribbit_map_error"), true);
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        }
        return super.use(level, player, hand);
    }
}
