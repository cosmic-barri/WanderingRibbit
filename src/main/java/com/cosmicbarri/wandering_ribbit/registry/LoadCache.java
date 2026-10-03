package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.entity.ItemCache;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LoadCache {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        ItemCache.buildCache();
    }
}