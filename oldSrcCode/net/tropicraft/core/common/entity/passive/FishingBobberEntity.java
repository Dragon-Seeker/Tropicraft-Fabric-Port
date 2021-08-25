package net.tropicraft.core.common.entity.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class FishingBobberEntity extends Entity {
   private static final TrackedData<Integer> DATA_HOOKED_ENTITY = DataTracker.registerData(FishingBobberEntity.class, TrackedDataHandlerRegistry.INTEGER);
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

   private FishingBobberEntity(World p_i50219_1_, EntityKoaBase koaBase, int luck, int lureSpeed) {
      super(EntityType.FISHING_BOBBER, p_i50219_1_);
      this.ignoreCameraFrustum = true;
      this.angler = koaBase;
      this.angler.setLure(this);
      this.luck = Math.max(0, luck);
      this.lureSpeed = Math.max(0, lureSpeed);
   }

   @Environment(EnvType.CLIENT)
   public FishingBobberEntity(World worldIn, EntityKoaBase p_i47290_2_, double x, double y, double z) {
      this(worldIn, p_i47290_2_, 0, 0);
      this.setPosition(x, y, z);
      this.prevX = this.getX();
      this.prevY = this.getY();
      this.prevZ = this.getZ();
   }

   public FishingBobberEntity(EntityKoaBase p_i50220_1_, World p_i50220_2_, int p_i50220_3_, int p_i50220_4_) {
      this(p_i50220_2_, p_i50220_1_, p_i50220_3_, p_i50220_4_);
      float f = this.angler.pitch;
      float f1 = this.angler.yaw;
      float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
      float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
      double d0 = this.angler.getX() - (double)f3 * 0.3D;
      double d1 = this.angler.getY() + (double)this.angler.getStandingEyeHeight();
      double d2 = this.angler.getZ() - (double)f2 * 0.3D;
      this.refreshPositionAndAngles(d0, d1, d2, f1, f);
      Vec3d Vector3d = new Vec3d(-f3, MathHelper.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
      double d3 = Vector3d.length();
      Vector3d = Vector3d.multiply(0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.random.nextGaussian() * 0.0045D);
      this.setVelocity(Vector3d);
      this.yaw = (float)(MathHelper.atan2(Vector3d.x, Vector3d.z) * (double)(180F / (float)Math.PI));
      this.pitch = (float)(MathHelper.atan2(Vector3d.y, MathHelper.sqrt(squaredDistanceTo(Vector3d))) * (double)(180F / (float)Math.PI));
      this.prevYaw = this.yaw;
      this.prevPitch = this.pitch;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(DATA_HOOKED_ENTITY, 0);
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> key) {
      if (DATA_HOOKED_ENTITY.equals(key)) {
         int i = this.getDataTracker().get(DATA_HOOKED_ENTITY);
         this.caughtEntity = i > 0 ? this.world.getEntityById(i - 1) : null;
      }

      super.onTrackedDataSet(key);
   }

   @Override
   @Environment(EnvType.CLIENT)
   public boolean shouldRender(double distance) {
      double d0 = 64.0D;
      return distance < 4096.0D;
   }

   @Override
   @Environment(EnvType.CLIENT)
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
   }

   @Override
   public void tick() {
      super.tick();

      if (this.angler == null) {
         if (this.age > 40) {
            remove();
         }
         return;
      }

      if (this.angler == null) {
         this.remove();
      } else if (this.world.isClient || !this.shouldStopFishing()) {
         if (this.inGround) {
            ++this.ticksInGround;
            if (this.ticksInGround >= 1200) {
               this.remove();
               return;
            }
         }

         float f = 0.0F;
         BlockPos blockpos = this.getBlockPos();
         FluidState ifluidstate = this.world.getFluidState(blockpos);
         if (ifluidstate.isIn(FluidTags.WATER)) {
            f = ifluidstate.getHeight(this.world, blockpos);
         }

         if (this.currentState == State.FLYING) {
            if (this.caughtEntity != null) {
               this.setVelocity(Vec3d.ZERO);
               this.currentState = State.HOOKED_IN_ENTITY;
               return;
            }

            if (f > 0.0F) {
               this.setVelocity(this.getVelocity().multiply(0.3D, 0.2D, 0.3D));
               this.currentState = State.BOBBING;
               return;
            }

            if (!this.world.isClient) {
               this.checkCollision();
            }

            if (!this.inGround && !this.onGround && !this.horizontalCollision) {
               ++this.ticksInAir;
            } else {
               this.ticksInAir = 0;
               this.setVelocity(Vec3d.ZERO);
            }
         } else {
            if (this.currentState == State.HOOKED_IN_ENTITY) {
               if (this.caughtEntity != null) {
                  if (this.caughtEntity.removed) {
                     this.caughtEntity = null;
                     this.currentState = State.FLYING;
                  } else {
                     setPosition(caughtEntity.getX(), caughtEntity.getBoundingBox().minY + (double) caughtEntity.getHeight() * 0.8D, caughtEntity.getZ());
                     setPosition(getX(), getY(), getZ());
                  }
               }

               return;
            }

            if (this.currentState == State.BOBBING) {
               Vec3d Vector3d = this.getVelocity();
               double d0 = this.getY() + Vector3d.y - (double)blockpos.getY() - (double)f;
               if (Math.abs(d0) < 0.01D) {
                  d0 += Math.signum(d0) * 0.1D;
               }

               this.setVelocity(Vector3d.x * 0.9D, Vector3d.y - d0 * (double)this.random.nextFloat() * 0.2D, Vector3d.z * 0.9D);
               if (!this.world.isClient && f > 0.0F) {
                  this.catchingFish(blockpos);
               }
            }
         }

         if (!ifluidstate.isIn(FluidTags.WATER)) {
            this.setVelocity(this.getVelocity().add(0.0D, -0.03D, 0.0D));
         }

         this.move(MovementType.SELF, this.getVelocity());
         this.updateRotation();
         double d1 = 0.92D;
         this.setVelocity(this.getVelocity().multiply(0.92D));
         this.setPosition(getX(), getY(), getZ());
      }
   }

   private boolean shouldStopFishing() {
      ItemStack itemstack = this.angler.getMainHandStack();
      ItemStack itemstack1 = this.angler.getOffHandStack();
      boolean flag = itemstack.getItem() instanceof net.minecraft.item.FishingRodItem;
      boolean flag1 = itemstack1.getItem() instanceof net.minecraft.item.FishingRodItem;
      if (!this.angler.removed && this.angler.isAlive() && (flag || flag1) && !(this.squaredDistanceTo(this.angler) > 1024.0D)) {
         return false;
      } else {
         this.remove();
         return true;
      }
   }

   private void updateRotation() {
      Vec3d Vector3d = this.getVelocity();
      float f = MathHelper.sqrt(squaredDistanceTo(Vector3d));
      this.yaw = (float)(MathHelper.atan2(Vector3d.x, Vector3d.z) * (double)(180F / (float)Math.PI));

      for(this.pitch = (float)(MathHelper.atan2(Vector3d.y, (double)f) * (double)(180F / (float)Math.PI)); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
      }

      while(this.pitch - this.prevPitch >= 180.0F) {
         this.prevPitch += 360.0F;
      }

      while(this.yaw - this.prevYaw < -180.0F) {
         this.prevYaw -= 360.0F;
      }

      while(this.yaw - this.prevYaw >= 180.0F) {
         this.prevYaw += 360.0F;
      }

      this.pitch = MathHelper.lerp(0.2F, this.prevPitch, this.pitch);
      this.yaw = MathHelper.lerp(0.2F, this.prevYaw, this.yaw);
   }

   private void checkCollision() {
      HitResult result = ProjectileUtil.getCollision(this, entity -> {
         return !entity.isSpectator() && (entity.collides() || entity instanceof ItemEntity) && (entity != this.angler || this.ticksInAir >= 5);
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
      this.getDataTracker().set(DATA_HOOKED_ENTITY, this.caughtEntity.getEntityId() + 1);
   }

   private void catchingFish(BlockPos p_190621_1_) {
      ServerWorld serverworld = (ServerWorld)this.world;
      int i = 1;
      BlockPos blockpos = p_190621_1_.up();
      if (this.random.nextFloat() < 0.25F && this.world.hasRain(blockpos)) {
         ++i;
      }

      if (this.random.nextFloat() < 0.5F && !this.world.isSkyVisibleAllowingSea(blockpos)) {
         --i;
      }

      if (this.ticksCatchable > 0) {
         --this.ticksCatchable;
         if (this.ticksCatchable <= 0) {
            this.ticksCaughtDelay = 0;
            this.ticksCatchableDelay = 0;
         } else {
            this.setVelocity(this.getVelocity().add(0.0D, -0.2D * (double)this.random.nextFloat() * (double)this.random.nextFloat(), 0.0D));
         }
      } else if (this.ticksCatchableDelay > 0) {
         this.ticksCatchableDelay -= i;
         if (this.ticksCatchableDelay > 0) {
            this.fishApproachAngle = (float)((double)this.fishApproachAngle + this.random.nextGaussian() * 4.0D);
            float f = this.fishApproachAngle * ((float)Math.PI / 180F);
            float f1 = MathHelper.sin(f);
            float f2 = MathHelper.cos(f);
            double d0 = this.getX() + (double)(f1 * (float)this.ticksCatchableDelay * 0.1F);
            double d1 = (float)MathHelper.floor(this.getBoundingBox().minY) + 1.0F;
            double d2 = this.getX() + (double)(f2 * (float)this.ticksCatchableDelay * 0.1F);
            Block block = serverworld.getBlockState(new BlockPos(d0, d1 - 1.0D, d2)).getBlock();
            if (serverworld.getBlockState(new BlockPos((int)d0, (int)d1 - 1, (int)d2)).getMaterial() == net.minecraft.block.Material.WATER) {
               if (this.random.nextFloat() < 0.15F) {
                  serverworld.spawnParticles(ParticleTypes.BUBBLE, d0, d1 - (double)0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
               }

               float f3 = f1 * 0.04F;
               float f4 = f2 * 0.04F;
               serverworld.spawnParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
               serverworld.spawnParticles(ParticleTypes.FISHING, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
            }
         } else {
            Vec3d Vector3d = this.getVelocity();
            this.setVelocity(Vector3d.x, (double)(-0.4F * MathHelper.nextFloat(this.random, 0.6F, 1.0F)), Vector3d.z);
            this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            double d3 = this.getBoundingBox().minY + 0.5D;
            serverworld.spawnParticles(ParticleTypes.BUBBLE, getX(), d3, getZ(), (int)(1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0D, this.getWidth(), 0.2F);
            serverworld.spawnParticles(ParticleTypes.FISHING, getX(), d3, getZ(), (int)(1.0F + this.getWidth() * 20.0F), this.getWidth(), 0.0D, this.getWidth(), 0.2F);
            this.ticksCatchable = MathHelper.nextInt(this.random, 20, 40);
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
            float f6 = MathHelper.nextFloat(this.random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
            float f7 = MathHelper.nextFloat(this.random, 25.0F, 60.0F);
            double d4 = getX() + (double)(MathHelper.sin(f6) * f7 * 0.1F);
            double d5 = (float)MathHelper.floor(this.getBoundingBox().minY) + 1.0F;
            double d6 = getZ() + (double)(MathHelper.cos(f6) * f7 * 0.1F);
            Block block1 = serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getBlock();
            if (serverworld.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getMaterial() == net.minecraft.block.Material.WATER) {
               serverworld.spawnParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
            }
         }

         if (this.ticksCaughtDelay <= 0) {
            this.fishApproachAngle = MathHelper.nextFloat(this.random, 0.0F, 360.0F);
            this.ticksCatchableDelay = MathHelper.nextInt(this.random, 20, 80);
         }
      } else {
         this.ticksCaughtDelay = MathHelper.nextInt(this.random, 100, 600);
         this.ticksCaughtDelay -= this.lureSpeed * 20 * 5;
      }

   }

   @Override
   public void writeCustomDataToNbt(NbtCompound compound) {
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   @Override
   public void readCustomDataFromNbt(NbtCompound compound) {
   }

   /**
    * Handler for {@link World#sendEntityStatus}
    */
   @Override
   @Environment(EnvType.CLIENT)
   public void handleStatus(byte id) {
      if (id == 31 && this.world.isClient && this.caughtEntity instanceof PlayerEntity && ((PlayerEntity)this.caughtEntity).isMainPlayer()) {
         this.bringInHookedEntity();
      }

      super.handleStatus(id);
   }

   protected void bringInHookedEntity() {
      if (angler != null) {
         Vec3d Vector3d = (new Vec3d(angler.getX() - getX(), angler.getY() - getY(), angler.getZ() - getZ())).multiply(0.1D);
         caughtEntity.setVelocity(caughtEntity.getVelocity().add(Vector3d));
      }
   }

   /**
    * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
    * prevent them from trampling crops
    */
   @Override
   protected boolean canClimb() {
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
   public boolean canUsePortals() {
      return false;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      Entity entity = this.getAngler();
      return new EntitySpawnS2CPacket(this, entity == null ? this.getEntityId() : entity.getEntityId());
   }

   enum State {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING
   }
}
