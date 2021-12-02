package net.tropicraft.core.common.block.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.tropicraft.core.common.registry.TropicBlockEntities;

public class TropicBambooChestBlockEntity extends ChestBlockEntity {

    private boolean unbreakable = false;

    public TropicBambooChestBlockEntity() {
        super(TropicBlockEntities.BAMBOO_CHEST);
    }

    /*
    public TropicBambooChestBlockEntity(BlockEntityType<ChestBlockEntity> blockEntityType, Identifier blockId) {
        super(TropicBlockEntities.BAMBOO_CHEST);
    }
     */

    @Override
    public Component getDisplayName() {
        //return getContainerName();
        return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.bamboo_chest");
        //return new TranslatableText(Tropicfabricport.MOD_ID + "container.bamboo_chest");
    }
    @Override
    public void load(BlockState state, CompoundTag compound) {
        super.load(state, compound);
        unbreakable = compound.getBoolean("unbreakable");
    }
    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putBoolean("unbreakable", unbreakable);

        return compound;
    }

    /**
     *
     * @return Returns if this chest is unbreakable
     */
    public boolean isUnbreakable() {
        return unbreakable;
    }

    /**
     * Sets whether this chest is unbreakable or not
     * @param flag Value to set the unbreakable flag to
     */
    public void setIsUnbreakable(boolean flag) {
        unbreakable = flag;
    }

}
