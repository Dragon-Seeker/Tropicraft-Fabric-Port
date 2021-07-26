package net.tropicraftFabric.common.block.blockentity;

import net.tropicraftFabric.common.registry.TropicBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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
    public Text getDisplayName() {
        //return getContainerName();
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.bamboo_chest");
        //return new TranslatableText(Tropicfabricport.MOD_ID + "container.bamboo_chest");
    }
    @Override
    public void fromTag(BlockState state, NbtCompound compound) {
        super.fromTag(state, compound);
        unbreakable = compound.getBoolean("unbreakable");
    }
    @Override
    public NbtCompound writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
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
