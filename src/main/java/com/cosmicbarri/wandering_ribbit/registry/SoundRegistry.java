package com.cosmicbarri.wandering_ribbit.registry;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WanderingRibbit.MODID);
    public static final RegistryObject<SoundEvent> WANDERING_RIBBIT_STEP = REGISTRY.register("wandering_ribbit_step", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_step")));
    public static final RegistryObject<SoundEvent> WANDERING_RIBBIT_HURT = REGISTRY.register("wandering_ribbit_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_hurt")));
    public static final RegistryObject<SoundEvent> WANDERING_RIBBIT_DEATH = REGISTRY.register("wandering_ribbit_death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_death")));
    public static final RegistryObject<SoundEvent> WANDERING_RIBBIT_AMBIENT = REGISTRY.register("wandering_ribbit_ambient", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "wandering_ribbit_ambient")));
}