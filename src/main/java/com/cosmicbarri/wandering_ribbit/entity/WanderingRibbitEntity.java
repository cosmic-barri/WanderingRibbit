package com.cosmicbarri.wandering_ribbit.entity;

import com.cosmicbarri.wandering_ribbit.registry.EntityRegistry;
import com.cosmicbarri.wandering_ribbit.registry.ItemRegistry;
import com.cosmicbarri.wandering_ribbit.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

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

    public WanderingRibbitEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(EntityRegistry.WANDERING_RIBBIT.get(), world);
    }

    public WanderingRibbitEntity(EntityType<WanderingRibbitEntity> type, Level world) {
        super(type, world);
        setNoAi(false);
        setMaxUpStep(1.1f);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockstate) {
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

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.offers.add(new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, 5), Items.COMPASS.getDefaultInstance(), ItemRegistry.RIBBIT_MAP.get().getDefaultInstance(), 5, 5, 1));
            this.offers.add(new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, 3), ItemRegistry.RIBBIT_UMBRELLA.get().getDefaultInstance(), 5, 5, 1));
        }
        return this.offers;
    }

    @Override
    public void overrideOffers(MerchantOffers merchantOffers) {}

    @Override
    public void notifyTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
    }

    @Override
    public void notifyTradeUpdated(ItemStack itemStack) {
        this.triggerAnim("controller", "dance");
        this.push(0, 0.1, 0);
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {}

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (!this.level().isClientSide) {
            this.openTradingScreen(player, this.getDisplayName(), 0);
            this.setTradingPlayer(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public @Nullable SoundEvent getNotifyTradeSound() {
        return SoundRegistry.WANDERING_RIBBIT_AMBIENT.get();
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }
}