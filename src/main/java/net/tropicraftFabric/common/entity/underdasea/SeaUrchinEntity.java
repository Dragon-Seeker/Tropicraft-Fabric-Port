package net.tropicraftFabric.common.entity.underdasea;

import net.tropicraftFabric.common.entity.egg.EggEntity;
import net.tropicraftFabric.common.entity.egg.SeaUrchinEggEntity;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.world.World;

public class SeaUrchinEntity extends EchinodermEntity {
    /**
     * Bounding box length/width/height of a freshly hatched sea urchin.
     */
    public static final float BABY_SIZE = 0.25f;

    /**
     * Bounding box length/width/height of a mature sea urchin.
     */
    public static final float ADULT_SIZE = 0.5f;

    /**
     * Rendered Y offset of a freshly hatched sea urchin.
     */
    public static final float BABY_YOFFSET = 0.125f;

    /**
     * Rendered Y offset of a mature sea urchin.
     */
    public static final float ADULT_YOFFSET = 0.25f;

    public SeaUrchinEntity(EntityType<? extends EchinodermEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return WaterCreatureEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0);
    }

    @Override
    public boolean damage(DamageSource source, float amt) {
        if (source.getName().equals("player")) {
            Entity ent = source.getAttacker();

            if (ent instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) ent;

                if (player.getMainHandStack().isEmpty()) {
                    player.damage(DamageSource.mob(this), 2);
                }
            }
        }

        return super.damage(source, amt);
    }

    @Override
    public void pushAwayFrom(Entity ent) {
        super.pushAwayFrom(ent);

        if (!world.isClient) {
            if (ent instanceof LivingEntity && !(ent instanceof SeaUrchinEntity) && !(ent instanceof SeaUrchinEggEntity)) {
                ent.damage(DamageSource.mob(this), 2);
            }
        }
    }

    @Override
    public EggEntity createEgg() {
        return new SeaUrchinEggEntity(TropicraftEntities.SEA_URCHIN_EGG_ENTITY, world);
    }

    @Override
    public float getBabyWidth() {
        return BABY_SIZE;
    }

    @Override
    public float getAdultWidth() {
        return ADULT_SIZE;
    }

    @Override
    public float getBabyHeight() {
        return BABY_SIZE;
    }

    @Override
    public float getAdultHeight() {
        return ADULT_SIZE;
    }

    @Override
    public float getBabyYOffset() {
        return BABY_YOFFSET;
    }

    @Override
    public float getAdultYOffset() {
        return ADULT_YOFFSET;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new MobSpawnS2CPacket(this);
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.SEA_URCHIN_SPAWN_EGG.get());
    }
     */
}
