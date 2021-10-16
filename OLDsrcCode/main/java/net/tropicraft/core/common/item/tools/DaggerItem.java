package net.tropicraft.core.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DaggerItem extends Item {
    private final TropicTiers tier;

    public DaggerItem(TropicTiers tier, FabricItemSettings properties) {
        super(properties.maxDamage(tier.getDefaultTier().getDurability()));

        this.tier = tier;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.REPLACEABLE_UNDERWATER_PLANT && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public boolean postHit(ItemStack itemStack, LivingEntity attacker, LivingEntity target) {
        itemStack.damage(1, target, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.BLOCK;
    }

    /*
    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 0x11940;
    }
     */

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.getBlock() == Blocks.COBWEB;
    }


    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> multimap = ImmutableMultimap.builder();

        multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) tier.getDefaultTier().getAttackDamage() + 2.5D, EntityAttributeModifier.Operation.ADDITION));
        multimap.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", 0, EntityAttributeModifier.Operation.ADDITION));

        if (slot == EquipmentSlot.MAINHAND) {
            return multimap.build();
        }

        else{
            return super.getAttributeModifiers(slot);
        }
    }




}
