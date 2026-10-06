package com.cosmicbarri.wandering_ribbit.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WanderingRibbitRenderer extends GeoEntityRenderer<WanderingRibbitEntity> {
    public WanderingRibbitRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WanderingRibbitModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public RenderType getRenderType(WanderingRibbitEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    public void preRender(PoseStack poseStack, WanderingRibbitEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.scaleHeight = 1f;
        this.scaleWidth = 1f;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}