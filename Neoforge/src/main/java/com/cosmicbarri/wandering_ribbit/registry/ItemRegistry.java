package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import com.cosmicbarri.wandering_ribbit.item.RibbitMap;
import com.cosmicbarri.wandering_ribbit.item.RibbitUmbrella;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(WanderingRibbit.MODID);
    public static final DeferredItem<Item> RIBBIT_MAP = REGISTRY.register("ribbit_map", RibbitMap::new);
    public static final DeferredItem<Item> RIBBIT_UMBRELLA = REGISTRY.register("ribbit_umbrella", RibbitUmbrella::new);
    public static final DeferredItem<Item> RIBBIT_EGG = REGISTRY.register("ribbit_egg", () -> new DeferredSpawnEggItem(EntityRegistry.WANDERING_RIBBIT, -11164863, -11569212, new Item.Properties()));
}
