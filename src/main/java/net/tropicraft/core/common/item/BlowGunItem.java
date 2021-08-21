package net.tropicraft.core.common.item;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class BlowGunItem extends RangedWeaponItem {

    public BlowGunItem(final FabricItemSettings settings) {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack heldStack = player.getStackInHand(hand);
        ItemStack ammo = getAmmo(player, heldStack);
        if (!ammo.isEmpty()) {
            fireProjectile(world, player, hand, heldStack, ammo, 1.0F, player.abilities.creativeMode, 10, 0);
            //return new ActionResult<>(ActionResultType.SUCCESS, heldStack);
            return TypedActionResult.success(heldStack);
        } else {
            return TypedActionResult.fail(heldStack);
        }
    }

    private static ItemStack getAmmo(LivingEntity entityIn, ItemStack stack) {
        final boolean isCreativeMode = entityIn instanceof PlayerEntity && ((PlayerEntity)entityIn).abilities.creativeMode;
        final ItemStack ammo = entityIn.getArrowType(stack);
        if (isCreativeMode) {
            return getProjectile();
        }
        if (!ammo.isEmpty()) {
            return ammo;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getProjectile() {
        ItemStack itemStack = new ItemStack(Items.TIPPED_ARROW);
        itemStack = PotionUtil.setCustomPotionEffects(itemStack, ImmutableList.of(new StatusEffectInstance(StatusEffects.SLOWNESS, 3 * 20, 20)));
        return itemStack;
    }

    public static void fireProjectile(World world, LivingEntity shooter, Hand hand, ItemStack heldItem, ItemStack projectile, float soundPitch, boolean isCreativeMode, float dmg, float pitch) {
        if (!world.isClient()) {
            PersistentProjectileEntity arrowEntity = createArrow(world, shooter, projectile);
            if (isCreativeMode) {
                //arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                arrowEntity.pickupType = arrowEntity.pickupType.CREATIVE_ONLY;
            }

            Vec3d lookVec = shooter.getRotationVector(); //getRotationVectorClient() ???
            //Vec3d lookVec = shooter.getLookVec();

            Quaternion quaternion = new Quaternion(new Vec3f(lookVec), 0, true);
            //Quaternion quaternion = new Quaternion(new Vector3f(lookVec), 0, true);



            Vec3d look = shooter.getRotationVec(1.0F);
            Vec3f look3f = new Vec3f(look);
            look3f.rotate(quaternion);
            //look3f.transform(quaternion);

            //TODO: Implement a breath in counter that determines either the speed and/or the damage
            float blowCount = 1.0F;

            arrowEntity.setDamage(dmg);
            arrowEntity.setVelocity(look3f.getX(), look3f.getY(), look3f.getZ());
            arrowEntity.pitch = pitch;
            //arrowEntity.setVelocity((double)look3f.getX(), (double)look3f.getY(), (double)look3f.getZ(), dmg, pitch); //shoot();

            heldItem.damage(1, shooter, (i) -> i.sendToolBreakStatus(hand));

            projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                ((PlayerEntity) shooter).inventory.removeOne(projectile);
            }

            world.spawnEntity(arrowEntity);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }
    /*
    public static ArrowEntity createArrow(World world, LivingEntity shooter, ItemStack projectile) {
        ArrowItem arrowItem = (ArrowItem) (projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
        ArrowEntity arrowEntity = (ArrowEntity) arrowItem.createArrow(world, projectile, shooter);
        arrowEntity.setDamage(0);
        arrowEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
        arrowEntity.setsetIsCritical(false);
        arrowEntity.setPotionEffect(getProjectile());
        return arrowEntity;
    }

     */


    public static ArrowEntity createArrow(World world, LivingEntity shooter, ItemStack projectile) {
        ArrowItem arrowItem = (ArrowItem) (projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
        ArrowEntity arrowEntity = (ArrowEntity) arrowItem.createArrow(world, projectile, shooter);
        arrowEntity.setDamage(0);
        arrowEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
        arrowEntity.setCritical(false);
        for(final StatusEffectInstance effectInstance : PotionUtil.getPotionEffects(getProjectile())){
            arrowEntity.addEffect(effectInstance);
        }

        //arrowEntity.setPotionEffect(getProjectile());
        return arrowEntity;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() { //getInventoryAmmoPredicate
        return (itemStack) -> {
            if (itemStack.getItem() == Items.TIPPED_ARROW) {
                for (final StatusEffectInstance effectInstance : PotionUtil.getPotionEffects(itemStack)) {
                    if (effectInstance.getEffectType() == StatusEffects.SLOWNESS) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    @Override
    public int getRange() {
        return 15; //???
    }
}
