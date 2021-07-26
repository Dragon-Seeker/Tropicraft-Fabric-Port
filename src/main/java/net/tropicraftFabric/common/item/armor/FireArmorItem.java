package net.tropicraftFabric.common.item.armor;

import net.tropicraftFabric.common.item.ArmorMaterials;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

import static net.fabricmc.api.EnvType.CLIENT;

public class FireArmorItem extends TropicraftArmorItem {
    public FireArmorItem(EquipmentSlot slotType, FabricItemSettings properties) {
        super(ArmorMaterials.FIRE_ARMOR, slotType, properties);
    }

    /*
    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == slot ? defaultModifiers : super.getAttributeModifiers(equipmentSlot);
    }

     */

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity user, int slot, boolean selected) {
        Random random = world.random;
        Iterable<ItemStack> armorItems = user.getArmorItems();
        for (ItemStack armorItem : armorItems) {
            if(armorItem == stack){
                if (world.isClient()) {
                    clientTick((PlayerEntity) user);
                } else {
                    if (user.isOnFire()) user.extinguish();

                    // Repair in the sun?
                    int factor = (int) (40D / (0.001D + world.getBrightness(user.getBlockPos())));
                    if (world.getTime() % (factor) == 0 && world.isSkyVisible(new BlockPos(MathHelper.floor(user.getX()), MathHelper.floor(user.getY() + 1), MathHelper.floor(user.getZ())))) {
                        //repair!
                        stack.damage(-1,(PlayerEntity) user, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        });
                    }

                }
            }
        }


    }

    /*
    // TODO waiting on Forge
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        Random random = world.random;
        if (world.isClient()) {
            clientTick((PlayerEntity) user);
        } else {
            if(user.isMobOrPlayer()) {
                if (user.isOnFire()) user.extinguish();

                // Repair in the sun?
                int factor = (int) (40D / (0.001D + world.getBrightness(user.getBlockPos())));
                if (world.getTime() % (factor) == 0 && world.isSkyVisible(new BlockPos(MathHelper.floor(user.getX()), MathHelper.floor(user.getY() + 1), MathHelper.floor(user.getZ())))) {
                    //repair!
                    stack.damage(-1, user, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                }
            }
        }
    }

     */

//    @Override
//    public void damageArmor(LivingEntity player, ItemStack stack, DamageSource source, int damage, int slot) {
//        if (source == DamageSource.IN_FIRE || source == DamageSource.LAVA) {
//            //cheap way to slow the damage
//            //if (player.worldObj.getWorldTime() % 2 == 0) {
//            stack.damageItem(damage, player);
//        }
//    }
//
    //@OnlyIn(Dist.CLIENT)
    @Environment(CLIENT)
    public void clientTick(PlayerEntity player) {
        /*
        Iterable<ItemStack> armorItems = player.getArmorItems();
        for (ItemStack armorItem : armorItems) {}
         */


        // Don't show fire particles underwater
        if (player.isSubmergedInWater()) return;

        float range = 0.2F;
        float speed = 0.08F;

        Random rand = new Random();
        World worldRef = player.world;

        int extraRand = 0;

        final Vec3d motion = player.getVelocity();//getMotion
        double plSpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);

        if (plSpeed < 0.1F) {
            extraRand = 7;
        }

        /** 0 for all, 1 for minimal, 2 for off */
        ParticlesMode particles = MinecraftClient.getInstance().options.particles;//Minecraft.getInstance().gameSettings.particles; //ParticleStatus
        if (particles == ParticlesMode.MINIMAL) return;

        if (this == TropicraftItems.FIRE_BOOTS) {
            boolean onLava = false;
            boolean inLava = false;
            //for (int x = -1; x < 2; x++) {
            //for (int z = -1; z < 2; z++) {
            int x = 0;
            int z = 0;
            if (motion.y < 0) {
                BlockState state = player.world.getBlockState(new BlockPos(MathHelper.floor(player.getX() + x), MathHelper.floor(player.getY() - 2), MathHelper.floor(player.getZ() + z)));
                if (state.getMaterial() == Material.LAVA) {
                    onLava = true;
                }
            }
            BlockState block2 = player.world.getBlockState(new BlockPos(MathHelper.floor(player.getX() + x), MathHelper.floor(player.getY() - 1), MathHelper.floor(player.getZ() + z)));
            if (block2.getMaterial() == Material.LAVA) {
                inLava = true;
            }

            // why do we do this on the client?
            if (onLava && !inLava) {
                player.setVelocity(motion.multiply(1, 0, 1));
                player.setOnGround(true);
            }

            // why do we do this on the client???????
            if (inLava) {
                if (plSpeed < 0.4D) {
                    player.setVelocity(motion.multiply(1.5D, 1.5D, 1.5D));
                }
            }

            float look = player.world.getTimeOfDay() * (10 + (onLava ? 10 : 0));
            double dist = 1F;

            double gatherX = player.getX();
            double gatherY = player.getBoundingBox().minY;
            double gatherZ = player.getZ();

            double motionX = ((rand.nextFloat() * speed) - (speed / 2));
            double motionZ = ((rand.nextFloat() * speed) - (speed / 2));

            final int numFeetParticles = particles == ParticlesMode.DECREASED ? 2 : 11;

            for (int i = 0; i < numFeetParticles + (onLava ? 5 : 0); i++) {
                motionX = (-Math.sin((look) / 180.0F * 3.1415927F) * Math.cos(0 / 180.0F * 3.1415927F) * (speed + (0.1 * rand.nextDouble())));
                motionZ = (Math.cos((look) / 180.0F * 3.1415927F) * Math.cos(0 / 180.0F * 3.1415927F) * (speed + (0.1 * rand.nextDouble())));

                ParticleEffect particle = ParticleTypes.FLAME;
                if (rand.nextInt(22) == 0) particle = ParticleTypes.LARGE_SMOKE;

                if (onLava || rand.nextInt(1 + extraRand) == 0) {
                    Vec3d motion1 = player.getVelocity();
                    player.world.addParticle(particle,
                            gatherX + ((rand.nextFloat() * range) - (range / 2)),
                            gatherY + ((rand.nextFloat() * range) - (range / 2)),
                            gatherZ + ((rand.nextFloat() * range) - (range / 2)),
                            motion1.x + motionX,
                            0.01F,
                            motion1.z + motionZ);

                    player.world.addParticle(particle,
                            (double) gatherX + ((rand.nextFloat() * range) - (range / 2)),
                            (double) gatherY + ((rand.nextFloat() * range) - (range / 2)),
                            (double) gatherZ + ((rand.nextFloat() * range) - (range / 2)),
                            motion1.x - motionX,
                            0.01F,
                            motion1.z - motionZ);
                }
            }

        }


        else if (this == TropicraftItems.FIRE_LEGGINGS) {
            ParticleEffect particle = ParticleTypes.FLAME;
            if (rand.nextInt(2) == 0) particle = ParticleTypes.LARGE_SMOKE;

            if (rand.nextInt(3 + extraRand) == 0) {
                player.world.addParticle(particle,
                        player.getX() + ((rand.nextFloat() * range) - (range/2)),
                        player.getY() - 0.8F + ((rand.nextFloat() * range) - (range/2)),
                        player.getZ() + ((rand.nextFloat() * range) - (range/2)),
                        ((rand.nextFloat() * speed) - (speed/2)),
                        -0.05F,
                        ((rand.nextFloat() * speed) - (speed/2)));
            }
        } else if (this == TropicraftItems.FIRE_CHESTPLATE) {
            float look = -180F;
            double dist = 0.5F;

            double gatherX = player.getX() + (-Math.sin((player.yaw+look) / 180.0F * 3.1415927F) * Math.cos(player.pitch / 180.0F * 3.1415927F) * dist);
            double gatherZ = player.getZ() + (Math.cos((player.yaw+look) / 180.0F * 3.1415927F) * Math.cos(player.pitch / 180.0F * 3.1415927F) * dist);

            ParticleEffect particle = ParticleTypes.FLAME;
            if (rand.nextInt(2) == 0) particle = ParticleTypes.LARGE_SMOKE;

            if (rand.nextInt(1 + extraRand) == 0) {
                player.world.addParticle(particle,
                        gatherX + ((rand.nextFloat() * range) - (range/2)),
                        player.getY() - 0.4F + ((rand.nextFloat() * range) - (range/2)),
                        gatherZ + ((rand.nextFloat() * range) - (range/2)),
                        ((rand.nextFloat() * speed) - (speed/2)),
                        -0.01F,
                        ((rand.nextFloat() * speed) - (speed/2)));
            }

        } else if (this == TropicraftItems.FIRE_HELMET) {
            float look = -180F;
            double dist = 0.5F;

            range = 2F;

            double gatherX = player.getX() + (-Math.sin((player.yaw+look) / 180.0F * 3.1415927F) * Math.cos(player.pitch / 180.0F * 3.1415927F) * dist);
            double gatherZ = player.getZ() + (Math.cos((player.yaw+look) / 180.0F * 3.1415927F) * Math.cos(player.pitch / 180.0F * 3.1415927F) * dist);

            ParticleEffect particle = ParticleTypes.FLAME;
            if (rand.nextInt(2) == 0) particle = ParticleTypes.LARGE_SMOKE;

            if (rand.nextInt(2) == 0) {
                player.world.addParticle(particle,
                        gatherX + ((rand.nextFloat() * range) - (range/2)),
                        player.getY() + 0.7F,
                        gatherZ + ((rand.nextFloat() * range) - (range/2)),
                        ((rand.nextFloat() * speed) - (speed/2)),
                        -0.01F,
                        ((rand.nextFloat() * speed) - (speed/2)));
            }
        }
    }
}
