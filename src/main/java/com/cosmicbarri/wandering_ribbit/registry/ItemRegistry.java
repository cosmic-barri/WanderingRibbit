package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import com.cosmicbarri.wandering_ribbit.item.RibbitMap;
import com.cosmicbarri.wandering_ribbit.item.RibbitUmbrella;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WanderingRibbit.MODID);
    public static final RegistryObject<Item> RIBBIT_MAP = REGISTRY.register("ribbit_map", RibbitMap::new);
    public static final RegistryObject<Item> RIBBIT_UMBRELLA = REGISTRY.register("ribbit_umbrella", RibbitUmbrella::new);
    public static final RegistryObject<Item> RIBBIT_EGG = REGISTRY.register("ribbit_egg", () -> new ForgeSpawnEggItem(EntityRegistry.WANDERING_RIBBIT, -11164863, -11569212, new Item.Properties()));
}
