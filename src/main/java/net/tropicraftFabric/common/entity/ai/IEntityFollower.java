package net.tropicraftFabric.common.entity.ai;

import net.minecraft.entity.LivingEntity;

public interface IEntityFollower {
    public LivingEntity getFollowingEntity();
    public void setFollowingEntity(LivingEntity entity);
}



