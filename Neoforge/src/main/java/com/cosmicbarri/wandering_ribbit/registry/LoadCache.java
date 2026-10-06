package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.entity.ItemCache;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@EventBusSubscriber
public class LoadCache {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        ItemCache.buildCache();
    }
}