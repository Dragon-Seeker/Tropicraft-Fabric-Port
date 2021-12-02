package net.tropicraft.core.common.entity.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.tropicraft.core.common.entity.passive.FishingBobberEntity.State;
import org.jetbrains.annotations.Nullable;


public class FishingBobberEntity extends Entity {
   private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(FishingBobberEntity.class, EntityDataSerializers.INT);
   private boolean inGround;
   private int ticksInGround;
   private final EntityKoaBase angler;
   private int ticksInAir;
   private int ticksCatchable;
   private int ticksCaughtDelay;
   private int ticksCatchableDelay;
   private float fishApproachAngle;
   public Entity caughtEntity;
   private State currentState = State.FLYING;
   private final int luck;
   private final int lureSpeed;

   private FishingBobberEntity(Level p_i50219_1_, EntityKoaBase koaBase, int luck, int lureSpeed) {
      super(EntityType.FISHING_BOBBER, p_i50219_1_);
      this.noCulling = true;
      this.angler = koaBase;
      this.angler.setLure(this);
      this.luck = Math.max(0, luck);
      this.lureSpeed = Math.max(0, lureSpeed);
   }

   @Environment(EnvType.CLIENT)
   public FishingBobberEntity(Level worldIn, EntityKoaBase p_i47290_2_, double x, double y, double z) {
      this(worldIn, p_i47290_2_, 0, 0);
      this.setPos(x, y, z);
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
   }

   public FishingBobberEntity(EntityKoaBase p_i50220_1_, Level p_i50220_2_, int p_i50220_3_, int p_i50220_4_) {
      this(p_i50220_2_, p_i50220_1_, p_i50220_3_, p_i50220_4_);
      float f = this.angler.xRot;
      float f1 = this.angler.yRot;
      float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
      float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
      double d0 = this.angler.getX() - (double)f3 * 0.3D;
      double d1 = this.angler.getY() + (double)this.angler.getEyeHeight();
      double d2 = this.angler.getZ() - (double)f2 * 0.3D;
      this.moveTo(d0, d1, d2, f1, f);
      Vec3 Vector3d = new Vec3(-f3, Mth.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
      double d3 = Vector3d.length();
      Vector3d = Vector3d.multiply(0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D);
      this.setDeltaMovement(Vector3d);
      this.yRot = (float)(Mth.atan2(Vector3d.x, Vector3d.z) * (double)(180F / (float)Math.PI));
      this.xRot = (float)(Mth.atan2(Vector3d.y, Mth.sqrt(distanceToSqr(Vector3d))) * (double)(180F / (float)Math.PI));
      this.yRotO = this.yRot;
      this.xRotO = this.xRot;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      if (DATA_HOOKED_ENTITY.equals(key)) {
         int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
         this.caughtEntity = i > 0 ? this.level.getEntity(i - 1) : null;
      }

      super.onSyncedDataUpdated(key);
   }

   @Override
   @Environment(EnvType.CLIENT)
   public boolean shouldRenderAtSqrDistance(double distance) {
      double d0 = 64.0D;
      return distance < 4096.0D;
   }

   @Override
   @Environment(EnvType.CLIENT)
   public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
   }

   @Override
   public void tick() {
      super.tick();

      if (this.angler == null) {
         if (this.tickCount > 40) {
            remove();
         }
         return;
      }

      if (this.angler == null) {
         this.remove();
      } else if (this.level.isClientSide || !this.shouldStopFishing()) {
         if (this.inGround) {
            ++this.ticksInGround;
            if (this.ticksInGround >= 1200) {
               this.remove();
               return;
            }
         }

         float f = 0.0F;
         BlockPos blockpos = this.blockPosition();
         FluidState ifluidstate = this.level.getFluidState(blockpos);
         if (ifluidstate.is(FluidTags.WATER)) {
            f = ifluidstate.getHeight(this.level, blockpos);
         }

         if (this.currentState == State.FLYING) {
            if (this.caughtEntity != null) {
               this.setDeltaMovement(Vec3.ZERO);
               this.currentState = State.HOOKED_IN_ENTITY;
               return;
            }

            if (f > 0.0F) {
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
               this.currentState = State.BOBBING;
               return;
            }

            if (!this.level.isClientSide) {
               this.checkCollision();
            }

            if (!this.inGround && !this.onGround && !this.horizontalCollision) {
               ++this.ticksInAir;
            } else {
               this.ticksInAir = 0;
               this.setDeltaMovement(Vec3.ZERO);
            }
         } else {
            if (this.currentState == State.HOOKED_IN_ENTITY) {
               if (this.caughtEntity != null) {
                  if (this.caughtEntity.removed) {
                     this.caughtEntity = null;
                     this.currentState = State.FLYING;
                  } else {
                     setPos(caughtEntity.getX(), caughtEntity.getBoundingBox().minY + (double) caughtEntity.getBbHeight() * 0.8D, caughtEntity.getZ());
                     setPos(getX(), getY(), getZ());
                  }
               }

               return;
            }

            if (this.currentState == State.BOBBING) {
               Vec3 Vector3d = this.getDeltaMovement();
               double d0 = this.getY() + Vector3d.y - (double)blockpos.getY() - (double)f;
               if (Math.abs(d0) < 0.01D) {
                  d0 += Math.signum(d0) * 0.1D;
               }

               this.setDeltaMovement(Vector3d.x * 0.9D, Vector3d.y - d0 * (double)this.random.nextFloat() * 0.2D, Vector3d.z * 0.9D);
               if (!this.level.isClientSide && f > 0.0F) {
                  this.catchingFish(blockpos);
               }
            }
         }

         if (!ifluidstate.is(FluidTags.WATER)) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         this.updateRotation();
         double d1 = 0.92D;
         this.setDeltaMovement(this.getDeltaMovement().scale(0.92D));
         this.setPos(getX(), getY(), getZ());
      }
   }

   private boolean shouldStopFishing() {
      ItemStack itemstack = this.angler.getMainHandItem();
      ItemStack itemstack1 = this.angler.getOffhandItem();
      boolean flag = itemstack.getItem() instanceof net.minecraft.world.item.FishingRodItem;
      boolean flag1 = itemstack1.getItem() instanceof net.minecraft.world.item.FishingRodItem;
      if (!this.angler.removed && this.angler.isAlive() && (flag || flag1) && !(this.distanceToSqr(this.angler) > 1024.0D)) {
         return false;
      } else {
         this.remove();
         return true;
      }
   }

   private void updateRotation() {
      Vec3 Vector3d = this.getDeltaMovement();
      float f = Mth.sqrt(distanceToSqr(Vector3d));
      this.yRot = (float)(Mth.atan2(Vector3d.x, Vector3d.z) * (double)(180F / (float)Math.PI));

      for(this.xRot = (float)(Mth.atan2(Vector3d.y, (double)f) * (double)(180F / (float)Math.PI)); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
      }

      while(this.xRot - this.xRotO >= 180.0F) {
         this.xRotO += 360.0F;
      }

      while(this.yRot - this.yRotO < -180.0F) {
         this.yRotO -= 360.0F;
      }

      while(this.yRot - this.yRotO >= 180.0F) {
         this.yRotO += 360.0F;
      }

      this.xRot = Mth.lerp(0.2F, this.xRotO, this.xRot);
      this.yRot = Mth.lerp(0.2F, this.yRotO, this.yRot);
   }

   private void checkCollision() {
      HitResult result = ProjectileUtil.getHitResult(this, entity -> {
         return !entity.isSpectator() && (entity.isPickable() || entity instanceof ItemEntity) && (entity != this.angler || this.ticksInAir >= 5);
      });
      if (result.getType() != HitResult.Type.MISS) {
         if (result.getType() == HitResult.Type.ENTITY) {
            this.caughtEntity = ((EntityHitResult)result).getEntity();
            this.setHookedEntity();
         } else {
            this.inGround = true;
         }
      }

   }

   private void setHookedEntity() {
      this.getEntityData().set(DATA_HOOKED_ENTITY, this.caughtEntity.getId() + 1);
   }

   private void catchingFish(BlockPos p_190621_1_) {
      ServerLevel serverworld = (ServerLevel)this.level;
      int i = 1;
      BlockPos blockpos = p_190621_1_.above();
      if (this.random.nextFloat() < 0.25F && this.level.isRainingAt(blockpos)) {
         ++i;
      }

      if (this.random.nextFloat() < 0.5F && !this.level.canSeeSkyFromBelowWater(blockpos)) {
         --i;
      }

      if (this.ticksCatchable > 0) {
         --this.ticksCatchable;
         if (this.ticksCatchable <= 0) {
            this.ticksCaughtDelay = 0;
            this.ticksCatchableDelay = 0;
         } else {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.2D * (double)this.random.nextFloat() * (double)this.random.nextFloat(), 0.0D));
         }
      } else if (this.ticksCatchableDelay > 0) {
         this.ticksCatchableDelay -= i;
         if (this.ticksCatchableDelay > 0) {
            this.fishApproachAngle = (float)((double)this.fishApproachAngle + this.random.nextGaussian() * 4.0D);
            float f = this.fishApproachAngle * ((float)Math.PI / 180F);
            float f1 = Mth.sin(f);
            float f2 = Mth.cos(f);
            double d0 = this.getX() + (double)(f1 * (float)this.ticksCatchableDelay * 0.1F);
            double d1 = (float)Mth.floor(this.getBoundingBox().minY) + 1.0F;
            double d2 = this.getX() + (double)(f2 * (float)this.ticksCatchableDelay * 0.1F);
            Block block = serverworld.getBlockState(new BlockPos(d0, d1 - 1.0D, d2)).getBlock();
            if (serverworld.getBlockState(new BlockPos((int)d0, (int)d1 - 1, (int)d2)).getMaterial() == net.minecraft.world.level.material.Material.WATER) {
               if (this.random.nextFloat() < 0.15F) {
                  serverworld.sendParticles(ParticleTypes.BUBBLE, d0, d1 - (double)0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
               }

               float f3 = f1 * 0.04F;
               float f4 = f2 * 0.04F;
               serverworld.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
               serverworld.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
            }
         } else {
            Vec3 Vector3d = this.getDeltaMovement();
            this.setDeltaMovement(Vector3d.x, (double)(-0.4F * Mth.nextFloat(this.random, 0.6F, 1.0F)), Vector3d.z);
            this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            double d3 = this.getBoundingBox().minY + 0.5D;
            serverworld.sendParticles(ParticleTypes.BUBBLE, getX(), d3, getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
            serverworld.sendParticles(ParticleTypes.FISHING, getX(), d3, getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
            this.ticksCatchable = Mth.nextInt(this.random, 20, 40);
         }
      } else if (this.ticksCaughtDelay > 0) {
         this.ticksCaughtDelay -= i;
         float f5 = 0.15F;
         if (this.ticksCaughtDelay < 20) {
            f5 = (float)((double)f5 + (double)(20 - this.ticksCaughtDelay) * 0.05D);
         } else if (this.ticksCaughtDelay < 40) {
            f5 = (float)((double)f5 + (double)(40 - this.ticksCaughtDelay) * 0.02D);
         } else if (this.ticksCaughtDelay < 60) {
            f5 = (float)((double)f5 + (double)(60 - this.ticksCaughtDelay) * 0.01D);
         }

         if (this.random.nextFloat() < f5) {
            float f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
            float f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
            double d4 = getX() + (double)(Mth.sin(f6) * f7 * 0.1F);
            double d5 = (float)Mth.floor(this.getBoundingBox().minY) + 1.0F;
            double d6 = getZ() + (double)(Mth.cos(f6) * f7 * 0.1F);
            Block block1 = serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getBlock();
            if (serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getMaterial() == net.minecraft.world.level.material.Material.WATER) {
               serverworld.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
            }
         }

         if (this.ticksCaughtDelay <= 0) {
            this.fishApproachAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
            this.ticksCatchableDelay = Mth.nextInt(this.random, 20, 80);
         }
      } else {
         this.ticksCaughtDelay = Mth.nextInt(this.random, 100, 600);
         this.ticksCaughtDelay -= this.lureSpeed * 20 * 5;
      }

   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
   }

   /**
    * Handler for {@link Level#broadcastEntityEvent}
    */
   @Override
   @Environment(EnvType.CLIENT)
   public void handleEntityEvent(byte id) {
      if (id == 31 && this.level.isClientSide && this.caughtEntity instanceof Player && ((Player)this.caughtEntity).isLocalPlayer()) {
         this.bringInHookedEntity();
      }

      super.handleEntityEvent(id);
   }

   protected void bringInHookedEntity() {
      if (angler != null) {
         Vec3 Vector3d = (new Vec3(angler.getX() - getX(), angler.getY() - getY(), angler.getZ() - getZ())).scale(0.1D);
         caughtEntity.setDeltaMovement(caughtEntity.getDeltaMovement().add(Vector3d));
      }
   }

   /**
    * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
    * prevent them from trampling crops
    */
   @Override
   protected boolean isMovementNoisy() {
      return false;
   }

   /**
    * Queues the entity for removal from the world on the next tick.
    */
   @Override
   public void remove() {
      super.remove();
      if (this.angler != null) {
         this.angler.setLure(null);
      }

   }

   @Nullable
   public EntityKoaBase getAngler() {
      return this.angler;
   }

   /**
    * Returns false if this Entity is a boss, true otherwise.
    */
   @Override
   public boolean canChangeDimensions() {
      return false;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      Entity entity = this.getAngler();
      return new ClientboundAddEntityPacket(this, entity == null ? this.getId() : entity.getId());
   }

   enum State {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING
   }
}
