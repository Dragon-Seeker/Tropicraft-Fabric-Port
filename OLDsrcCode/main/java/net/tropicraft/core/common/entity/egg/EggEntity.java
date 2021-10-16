package net.tropicraft.core.common.entity.egg;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

public abstract class EggEntity extends LivingEntity {

    private static final TrackedData<Integer> HATCH_DELAY = DataTracker.registerData(EggEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public double rotationRand;
   
    public EggEntity(final EntityType<? extends EggEntity> type, World w) {
        super(type, w);
        rotationRand = 0;
        ignoreCameraFrustum = true;
       
        this.setYaw(random.nextInt(360));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        age = compound.getInt("ticks");
        setHatchDelay(compound.getInt("hatchDelay"));
        super.readCustomDataFromNbt(compound);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        compound.putInt("ticks", age);
        compound.putInt("hatchDelay", getHatchDelay());
        super.writeCustomDataToNbt(compound);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(HATCH_DELAY, 0);
        setHatchDelay(-60 + random.nextInt(120));
    }

    public abstract boolean shouldEggRenderFlat();
    
    public abstract String getEggTexture();
    
    /**
     * Create and return a Entity here
     */
    public abstract Entity onHatch();
    
    /**
     * The amount of time in ticks it will take for the egg to hatch
     *     eg. hatch on tick n
     * @return a positive number
     */
    public abstract int getHatchTime();
    
    /**
     * The amount of time in ticks the egg will move around before it hatches
     *  eg. start rolling n ticks before hatch
     * @return a positive number lower than getHatchTime()
     */
    public abstract int getPreHatchMovement();
    
    public int getRandomHatchDelay() {
        return this.getDataTracker().get(HATCH_DELAY);
    }
     
    public boolean isHatching() {
        return this.age > (getHatchTime() + getRandomHatchDelay());
    }
    
    public boolean isNearHatching() {
        return this.age > (getHatchTime() + getRandomHatchDelay()) - getPreHatchMovement();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        
        if (isNearHatching()) {
            rotationRand += 0.1707F * world.random.nextFloat();
            
            // Hatch time!
            if (age >= this.getHatchTime()) {
                if (!world.isClient) {
                    final Entity ent = onHatch();
                    ent.refreshPositionAndAngles(getX(), getY(), getZ(), 0.0F, 0.0F);
                    world.spawnEntity(ent);
                    remove(RemovalReason.DISCARDED);
                }
            }
        } 
    }
    
    public void setHatchDelay(int i) {
        this.getDataTracker().set(HATCH_DELAY, -60 + random.nextInt(120));
    }
    
    public int getHatchDelay() {
        return this.getDataTracker().get(HATCH_DELAY);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slotIn, ItemStack stack) {
    }

    @Override
    public Arm getMainArm() {
        return Arm.LEFT;
    }
}
