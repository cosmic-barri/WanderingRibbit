package com.cosmicbarri.wandering_ribbit.entity;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WanderingRibbitModel extends GeoModel<WanderingRibbitEntity> {
    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "geo/wandering_ribbit.geo.json");
    private static final ResourceLocation ANIM = ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "animations/wandering_ribbit.animation.json");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WanderingRibbit.MODID, "textures/entity/wandering_ribbit.png");
    @Override
    public ResourceLocation getModelResource(WanderingRibbitEntity entity) {
        return MODEL;
    }
    @Override
    public ResourceLocation getAnimationResource(WanderingRibbitEntity entity) {
        return ANIM;
    }
    @Override
    public ResourceLocation getTextureResource(WanderingRibbitEntity entity) {
        return TEXTURE;
    }
}