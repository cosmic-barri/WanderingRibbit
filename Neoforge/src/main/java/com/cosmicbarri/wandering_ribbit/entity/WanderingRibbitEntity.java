package com.cosmicbarri.wandering_ribbit.entity;

import com.cosmicbarri.wandering_ribbit.WanderingRibbit;
import com.cosmicbarri.wandering_ribbit.registry.ItemRegistry;
import com.cosmicbarri.wandering_ribbit.registry.SoundRegistry;
import com.mojang.serialization.DataResult;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.Objects;
import java.util.Optional;

public class WanderingRibbitEntity extends PathfinderMob implements GeoEntity, Merchant {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation IDLE_HOLDING = RawAnimation.begin().thenLoop("idle_holding");
    private static final RawAnimation WALK_HOLDING = RawAnimation.begin().thenLoop("walk_holding");
    private static final RawAnimation DANCE = RawAnimation.begin().thenPlayXTimes("dance", 3);

    @Nullable
    private Player tradingPlayer;
    @Nullable
    protected MerchantOffers offers;

    public WanderingRibbitEntity(EntityType<WanderingRibbitEntity> type, Level world) {
        super(type, world);
        setNoAi(false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (!this.level().isClientSide) {
            MerchantOffers merchantoffers = this.getOffers();
            if (!merchantoffers.isEmpty()) {
                tag.put("Offers", MerchantOffers.CODEC.encodeStart(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), merchantoffers).getOrThrow());
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Offers")) {
            DataResult<MerchantOffers> result = MerchantOffers.CODEC.parse(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), tag.get("Offers"));
            Logger logger = WanderingRibbit.LOGGER;
            Objects.requireNonNull(logger);
            result.resultOrPartial(Util.prefix("Failed to load offers: ", logger::warn)).ifPresent((offers) -> this.offers = offers);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.DROWN))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockstate) {
        super.playStepSound(pos, blockstate);
        this.playSound(SoundRegistry.WANDERING_RIBBIT_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public SoundEvent getAmbientSound(){
        return SoundRegistry.WANDERING_RIBBIT_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return SoundRegistry.WANDERING_RIBBIT_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundRegistry.WANDERING_RIBBIT_DEATH.get();
    }

    @Override
    public boolean removeWhenFarAway(double idk) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 16);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        builder = builder.add(Attributes.STEP_HEIGHT, 1.1f);
        return builder;
    }

    public boolean isInRain() {
        return this.level().isRainingAt(this.blockPosition());
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        if (state.isMoving())
            return  state.setAndContinue(this.isInRain() ? WALK_HOLDING : WALK);
        return  state.setAndContinue(this.isInRain() ? IDLE_HOLDING : IDLE);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::predicate).triggerableAnim("dance", DANCE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
        if (this.getTradingPlayer() != null && player == null)
            this.setTradingPlayer(null);
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    private MerchantOffer getRandomOffer() {
        Item item = ItemCache.getRandomItem(this.random);
        return new MerchantOffer(new ItemCost(Items.AMETHYST_SHARD, 5), new ItemStack(item, item.getDefaultInstance().getMaxStackSize() != 1 ? 8 : 1), 5, 5, 1);
    }

    private void updateOffers() {
        this.offers = null;
        this.getOffers();
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.offers.add(new MerchantOffer(new ItemCost(Items.AMETHYST_SHARD, 5), Optional.of(new ItemCost(Items.COMPASS, 1)), ItemRegistry.RIBBIT_MAP.get().getDefaultInstance(), 5, 5, 1));
            this.offers.add(new MerchantOffer(new ItemCost(Items.AMETHYST_SHARD, 3), ItemRegistry.RIBBIT_UMBRELLA.get().getDefaultInstance(), 5, 5, 1));
            if (ItemCache.getRandomItem(this.random) != ItemStack.EMPTY.getItem()) {
                this.offers.add(getRandomOffer());
                this.offers.add(getRandomOffer());
                this.offers.add(getRandomOffer());
            }
        }
        return this.offers;
    }

    @Override
    public void overrideOffers(@NotNull MerchantOffers merchantOffers) {}

    @Override
    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        this.triggerAnim("controller", "dance");
        this.push(0, 0.1, 0);
    }

    @Override
    public void notifyTradeUpdated(@NotNull ItemStack itemStack) {}

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {}

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (this.getTradingPlayer() != null) return InteractionResult.FAIL;
        if (player.getMainHandItem().is(Items.AMETHYST_SHARD) && player.isCrouching()) {
            if (this.level() instanceof ServerLevel sl) {
                player.getMainHandItem().shrink(1);
                this.updateOffers();
                sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY(), this.getZ(), 15, 0.5, 0.5, 0.5, 0.0);
                this.playSound(SoundRegistry.WANDERING_RIBBIT_AMBIENT.get(), 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (!this.getOffers().isEmpty()) {
            if (!this.level().isClientSide) {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 0);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return SoundRegistry.WANDERING_RIBBIT_AMBIENT.get();
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }
}