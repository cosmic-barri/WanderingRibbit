package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, WanderingRibbit.MODID);
    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_RIBBIT_AMBIENT = REGISTRY.register("wandering_ribbit_ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_ambient")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_RIBBIT_HURT = REGISTRY.register("wandering_ribbit_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_hurt")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_RIBBIT_DEATH = REGISTRY.register("wandering_ribbit_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_death")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WANDERING_RIBBIT_STEP = REGISTRY.register("wandering_ribbit_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_step")));
}