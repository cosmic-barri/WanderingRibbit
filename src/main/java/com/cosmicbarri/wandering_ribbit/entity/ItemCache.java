package com.cosmicbarri.wandering_ribbit.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ItemCache {
    private static final List<Item> CACHE = new ArrayList<>();
    public static void buildCache() {
        CACHE.clear();
        if (ModList.get().isLoaded("ribbits"))
            for (Map.Entry<ResourceKey<Item>, Item> entry : ForgeRegistries.ITEMS.getEntries()) {
                ResourceLocation id = entry.getKey().location();
                Item item = entry.getValue();
                String itemId = id.toString();

                if (itemId.startsWith("ribbits"))
                    if (!(item instanceof SpawnEggItem))
                        CACHE.add(item);
            }
    }

    public static Item getRandomItem(RandomSource random) {
        return CACHE.isEmpty() ? ItemStack.EMPTY.getItem() : CACHE.get(random.nextInt(CACHE.size()));
    }
}