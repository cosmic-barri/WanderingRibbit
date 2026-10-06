package com.cosmicbarri.wandering_ribbit;

import com.cosmicbarri.wandering_ribbit.registry.EntityRegistry;
import com.cosmicbarri.wandering_ribbit.registry.ItemRegistry;
import com.cosmicbarri.wandering_ribbit.registry.SoundRegistry;
import com.cosmicbarri.wandering_ribbit.registry.TabRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WanderingRibbit.MODID)
public class WanderingRibbit {
    public static final String MODID = "wandering_ribbit";
    public WanderingRibbit() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        SoundRegistry.REGISTRY.register(bus);
        ItemRegistry.REGISTRY.register(bus);
        EntityRegistry.REGISTRY.register(bus);
        TabRegistry.REGISTRY.register(bus);
    }
}



