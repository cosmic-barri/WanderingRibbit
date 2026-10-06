package com.cosmicbarri.wandering_ribbit;

import com.cosmicbarri.wandering_ribbit.registry.EntityRegistry;
import com.cosmicbarri.wandering_ribbit.registry.ItemRegistry;
import com.cosmicbarri.wandering_ribbit.registry.SoundRegistry;
import com.cosmicbarri.wandering_ribbit.registry.TabRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(WanderingRibbit.MODID)
public class WanderingRibbit {
    public static final String MODID = "wandering_ribbit";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public WanderingRibbit(IEventBus bus) {
        EntityRegistry.REGISTRY.register(bus);
        ItemRegistry.REGISTRY.register(bus);
        SoundRegistry.REGISTRY.register(bus);
        TabRegistry.REGISTRY.register(bus);
    }
}
