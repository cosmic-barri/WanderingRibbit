package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WanderingRibbit.MODID);

    @SubscribeEvent
    public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
        if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            tabData.accept(ItemRegistry.RIBBIT_EGG.get());
        }
        if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            tabData.accept(ItemRegistry.RIBBIT_MAP);
            tabData.accept(ItemRegistry.RIBBIT_UMBRELLA);
        }
    }
}