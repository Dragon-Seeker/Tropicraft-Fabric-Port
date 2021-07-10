package com.dragonseeker.tropicfabricport.item.tools;

import com.dragonseeker.tropicfabricport.registry.TropicItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;

public class SpearItem extends TridentItem {

    private final TropicItems.TropicTiers tier;
    private final int attackDamage;
    private final float attackSpeed;

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public SpearItem(TropicItems.TropicTiers tier, int attackDamage, float attackSpeed, FabricItemSettings properties) {
        super(properties.maxDamage(tier.getDefaultTier().getDurability()));

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();

        this.tier = tier;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        // TODO
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }

        else{
            return super.getAttributeModifiers(slot);
        }
    }

    @Override
    public int getEnchantability() {
       return this.tier.getDefaultTier().getEnchantability();
    }

    @Override
    public boolean canRepair(ItemStack toRepair, ItemStack repair) {
       return this.tier.getDefaultTier().getRepairIngredient().test(repair) || super.canRepair(toRepair, repair);
    }
}
