package com.cosmicbarri.wandering_ribbit.entity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WanderingRibbitModel extends GeoModel<WanderingRibbitEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation("wandering_ribbit", "geo/wandering_ribbit.geo.json");
    private static final ResourceLocation ANIM = new ResourceLocation("wandering_ribbit", "animations/wandering_ribbit.animation.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation("wandering_ribbit", "textures/entity/wandering_ribbit.png");

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