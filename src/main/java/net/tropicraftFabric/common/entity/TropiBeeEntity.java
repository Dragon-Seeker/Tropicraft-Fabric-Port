package net.tropicraftFabric.common.entity;

import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class TropiBeeEntity extends BeeEntity {
    public TropiBeeEntity(EntityType<? extends BeeEntity> type, World world) {
        super(type, world);
    }

    @Override
    public BeeEntity createChild(ServerWorld world, PassiveEntity partner) {
        return TropicraftEntities.TROPI_BEE.create(this.world);
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.TROPIBEE_SPAWN_EGG);
    }
     */
}
