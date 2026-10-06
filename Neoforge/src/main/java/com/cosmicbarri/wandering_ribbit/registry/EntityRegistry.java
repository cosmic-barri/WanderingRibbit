package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import com.cosmicbarri.wandering_ribbit.entity.WanderingRibbitEntity;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.core.registries.Registries;

@EventBusSubscriber
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, WanderingRibbit.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<WanderingRibbitEntity>> WANDERING_RIBBIT = register("wandering_ribbit",
            EntityType.Builder.of(WanderingRibbitEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
                    .sized(0.8f, 0.8f));
    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WANDERING_RIBBIT.get(), WanderingRibbitEntity.createAttributes().build());
    }
}