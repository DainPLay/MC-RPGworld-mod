package net.dainplay.rpgworldmod.world.entity;

import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class ProjectruffleArrowEntity extends AbstractArrow {
    // default constructor, required to register the entity
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

    // syncs to the client
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}