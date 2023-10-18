package net.dainplay.rpgworldmod.world.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ProjectruffleArrowEntity extends AbstractArrow {
    // default constructor, required to register the entity

    @Override
    public double getBaseDamage() {
        return 0.0F;
    }
    @Override
    protected float getWaterInertia() {
        return 1.0F;
    }
    public ProjectruffleArrowEntity(EntityType<ProjectruffleArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ProjectruffleArrowEntity(EntityType<ProjectruffleArrowEntity> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
    }

    // the constructor used by the ArrowItem
    public ProjectruffleArrowEntity(EntityType<ProjectruffleArrowEntity> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
    }

    // the item stack to give the player when they walk over your arrow stuck in the ground
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.PROJECTRUFFLE_ITEM.get());
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    // syncs to the client
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}