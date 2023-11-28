package net.dainplay.rpgworldmod.entity.custom;

import net.dainplay.rpgworldmod.effect.ModEffects;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class Mintobat extends Monster {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Mintobat.class, EntityDataSerializers.BYTE);

    public Mintobat(EntityType<? extends net.dainplay.rpgworldmod.entity.custom.Mintobat> p_32219_, Level p_32220_) {
        super(p_32219_, p_32220_);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.xpReward = 10;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new net.dainplay.rpgworldmod.entity.custom.Mintobat.MintobatAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.FLYING_SPEED, (double)0.4F).add(Attributes.MOVEMENT_SPEED, (double)0.4F).add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            if (!this.level.isClientSide) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0.18, 0));
            }

            return super.hurt(pSource, pAmount);
        }
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 1.0F;
    }
    public void travel(Vec3 pTravelVector) {
        if (this.isInWater()) {
            this.moveRelative(0.02F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            float f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
        }

        this.calculateEntityAnimation(this, false);
    }
    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        return pPotioneffect.getEffect() == ModEffects.MOSSIOSIS.get() ? false : super.canBeAffected(pPotioneffect);
    }
    protected SoundEvent getAmbientSound() {
        return ModSounds.MINTOBAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.MINTOBAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.MINTOBAT_DEATH;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    public boolean isCharged() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    void setCharged(boolean pOnFire) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pOnFire) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    static class MintobatAttackGoal extends Goal {
        private final net.dainplay.rpgworldmod.entity.custom.Mintobat mintobat;
        private int attackStep;
        private int attackTime;
        private int lastSeen;
        protected final Random random = new Random();

        public MintobatAttackGoal(net.dainplay.rpgworldmod.entity.custom.Mintobat p_32247_) {
            this.mintobat = p_32247_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.mintobat.getTarget();
            return livingentity != null && livingentity.isAlive() && this.mintobat.canAttack(livingentity);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.attackStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.mintobat.setCharged(false);
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.mintobat.getTarget();
            if (livingentity != null) {
                boolean flag = this.mintobat.getSensing().hasLineOfSight(livingentity);
                if (flag) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d0 = this.mintobat.distanceToSqr(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.mintobat.doHurtTarget(livingentity);
                    }

                    this.mintobat.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 20;
                        } else if (this.attackStep <= 2) {
                            this.attackTime = 20;
                        } else {
                            this.attackTime = 120;
                            this.attackStep = 0;
                            this.mintobat.setCharged(false);
                        }

                        if (this.attackStep > 1) {
                            if (!this.mintobat.isSilent()) {
                                this.mintobat.playSound(ModSounds.MINTOBAT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);;
                            }


                            this.mintobat.setCharged(true);
                            ((ServerLevel)this.mintobat.level).sendParticles(ParticleTypes.EXPLOSION, this.mintobat.getX(), this.mintobat.getY(0.5D), this.mintobat.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            this.mintobat.level.getEntities(this.mintobat, this.mintobat.getBoundingBox().inflate(this.getFollowDistance()), target -> target instanceof LivingEntity && !(target instanceof Mintobat))
                                    .forEach(target -> {
                                        BlockPos entityPos = new BlockPos(this.mintobat.getX(), this.mintobat.getEyeY(), this.mintobat.getZ());
                                        Vec3 targetPos = new Vec3(target.getX(), target.getEyeY(), target.getZ());
                                        if (!isBlockInLine(this.mintobat.level, entityPos, targetPos)) {
                                                double scaledDamage = this.mintobat.getAttributeValue(Attributes.ATTACK_DAMAGE) * (1 - Math.min(1, this.mintobat.distanceTo(target) / this.getFollowDistance()));
                                                if (isEyeInAnyFluid(target)) scaledDamage /= 4;
                                                (target).hurt((new IndirectEntityDamageSource("scream", this.mintobat, this.mintobat)).damageHelmet().bypassArmor().setScalesWithDifficulty(), (float) scaledDamage);
                                        }
                                    });
                        }
                    }

                    this.mintobat.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.mintobat.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.mintobat.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }

    public static boolean isEyeInAnyFluid(Entity entity) {
        FluidState fluidState = entity.level.getFluidState(entity.eyeBlockPosition());
        return !fluidState.isEmpty();
    }

    private static boolean isBlockInLine(Level world, BlockPos start, Vec3 end) {
        Vec3 startVec3 = new Vec3(start.getX() + 0.5, start.getY() + 1.5, start.getZ() + 0.5); // Adjusted to account for eye position
        Vec3 targetVec3 = new Vec3(end.x, end.y, end.z);

        double dx = targetVec3.x - startVec3.x;
        double dy = targetVec3.y - startVec3.y;
        double dz = targetVec3.z - startVec3.z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double incrementX = dx / distance * 0.1;
        double incrementY = dy / distance * 0.1;
        double incrementZ = dz / distance * 0.1;

        double currX = startVec3.x;
        double currY = startVec3.y;
        double currZ = startVec3.z;

        // Step through each position in the line
        for (double i = 0; i < distance; i += 0.1) {
            BlockPos blockPos = new BlockPos(currX, currY, currZ);
            if (!world.isEmptyBlock(blockPos) && world.getBlockState(blockPos).is(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                return true; // Occluding block found
            }
            currX += incrementX;
            currY += incrementY;
            currZ += incrementZ;
        }
        return false; // No occluding block found
    }

}


