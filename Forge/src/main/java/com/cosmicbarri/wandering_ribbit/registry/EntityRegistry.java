package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import com.cosmicbarri.wandering_ribbit.entity.WanderingRibbitEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WanderingRibbit.MODID);
    public static final RegistryObject<EntityType<WanderingRibbitEntity>> WANDERING_RIBBIT = register(
            EntityType.Builder.<WanderingRibbitEntity>of(WanderingRibbitEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(WanderingRibbitEntity::new)
                   .sized(0.8f, 0.8f));
    private static <T extends Entity> RegistryObject<EntityType<T>> register(EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register("wandering_ribbit", () -> entityTypeBuilder.build("wandering_ribbit"));
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WANDERING_RIBBIT.get(), WanderingRibbitEntity.createAttributes().build());
    }
}