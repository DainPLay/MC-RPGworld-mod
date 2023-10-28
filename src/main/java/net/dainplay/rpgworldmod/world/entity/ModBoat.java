package net.dainplay.rpgworldmod.world.entity;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModBoat extends Boat {
    public ModBoat(EntityType<? extends ModBoat> type, Level level)
    {
        super(type, level);
        this.blocksBuilding = true;
    }

    public ModBoat(Level level, double x, double y, double z)
    {
        this(ModEntities.MODBOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt)
    {
        nbt.putString("Type", this.getModBoatType().getName());
    }

    public void setType(ModBoat.Type pBoatType) {
        this.entityData.set(DATA_ID_TYPE, pBoatType.ordinal());
    }

    public ModBoat.Type getModBoatType() {
        return ModBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag nbt)
    {
        if (nbt.contains("Type", 8)) {
            this.setType(ModBoat.Type.byName(nbt.getString("Type")));
        }
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (pOnGround) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != Boat.Status.ON_LAND) {
                        this.resetFallDistance();
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                    if (!this.level.isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                this.spawnAtLocation(this.getModBoatType().getPlanks());
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.resetFallDistance();
            } else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && pY < 0.0D) {
                this.fallDistance -= (float)pY;
            }

        }
    }

    @Override
    public Item getDropItem()
    {
        switch (ModBoat.Type.byId(this.entityData.get(DATA_ID_TYPE)))
        {
            case RIE:
                return ModItems.RIE_BOAT.get();
        }
        return Items.OAK_BOAT;
    }

    public enum Type {
        RIE(ModBlocks.RIE_PLANKS.get(), "rie");

        private final String name;
        private final Block planks;

        private Type(Block p_38427_, String p_38428_) {
            this.name = p_38428_;
            this.planks = p_38427_;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static ModBoat.Type byId(int pId) {
            ModBoat.Type[] aboat$type = values();
            if (pId < 0 || pId >= aboat$type.length) {
                pId = 0;
            }

            return aboat$type[pId];
        }

        public static ModBoat.Type byName(String pName) {
            ModBoat.Type[] aboat$type = values();

            for(int i = 0; i < aboat$type.length; ++i) {
                if (aboat$type[i].getName().equals(pName)) {
                    return aboat$type[i];
                }
            }

            return aboat$type[0];
        }
    }
}
