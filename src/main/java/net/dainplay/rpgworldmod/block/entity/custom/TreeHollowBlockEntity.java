package net.dainplay.rpgworldmod.block.entity.custom;

import net.dainplay.rpgworldmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TreeHollowBlockEntity extends BlockEntity implements Clearable {
        private ItemStack record = ItemStack.EMPTY;

        public TreeHollowBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
            super(ModBlockEntities.TREE_HOLLOW_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        }

        public void load(CompoundTag pTag) {
            super.load(pTag);
            if (pTag.contains("RecordItem", 10)) {
                this.setRecord(ItemStack.of(pTag.getCompound("RecordItem")));
            }

        }

        protected void saveAdditional(CompoundTag pTag) {
            super.saveAdditional(pTag);
            if (!this.getRecord().isEmpty()) {
                pTag.put("RecordItem", this.getRecord().save(new CompoundTag()));
            }

        }

        public ItemStack getRecord() {
            return this.record;
        }

        public void setRecord(ItemStack pRecord) {
            this.record = pRecord;
            this.setChanged();
        }

        public void clearContent() {
            this.setRecord(ItemStack.EMPTY);
        }
    }
