package com.dragonseeker.tropicfabricport.common.item;

import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import com.dragonseeker.tropicfabricport.client.data.TropicraftTags;

public class ArmorMaterials {
    public static final ArmorMaterial ASHEN_MASK = new AshenMask();
    public static final ArmorMaterial NIGEL_STACHE = new NigelStache();
    public static final ArmorMaterial SCALE_ARMOR = createArmorMaterial(
            18,
            new int[] {2, 5, 6, 2},
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            Ingredient.ofItems(TropicItems.SCALE),
            "scale",
            0.5f
    );
    public static final ArmorMaterial FIRE_ARMOR = createArmorMaterial(
            12,
            new int[] {2, 4, 5, 2},
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            null,
            "fire",
            0.1f
    );
    public static final ArmorMaterial SCUBA = createArmorMaterial(
            10, 
            new int[] {0, 0, 0, 0},
            0,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            null,
            "scuba_goggles",
            0);

    private static class AshenMask implements ArmorMaterial {
        @Override
        public int getDurability(EquipmentSlot slotIn) {
            return 10;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slotIn) {
            return slotIn == EquipmentSlot.HEAD ? 1 : 0;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.fromTag(TropicraftTags.Items.ASHEN_MASKS);
        }

        @Override
        public String getName() {
            return "mask";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }

    private static class NigelStache implements ArmorMaterial {

        @Override
        public int getDurability(EquipmentSlot slotIn) {
            return 10;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slotIn) {
            return slotIn == EquipmentSlot.HEAD ? 1 : 0;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(TropicItems.NIGEL_STACHE);
        }

        @Override
        public String getName() {
            return "nigel";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }

    public static ArmorMaterial createArmorMaterial(final int durability,
                                                    final int[] dmgReduction,
                                                    final int enchantability,
                                                    final SoundEvent soundEvent,
                                                    final Ingredient repairMaterial,
                                                    final String name,
                                                    final float toughness) {
        return new ArmorMaterial() {
            @Override
            public int getDurability(EquipmentSlot equipmentSlotType) {
                return durability;
            }

            @Override
            public int getProtectionAmount(EquipmentSlot equipmentSlot) {
                return dmgReduction[equipmentSlot.getEntitySlotId()];
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            @Override
            public SoundEvent getEquipSound() {
                return soundEvent;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return repairMaterial;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public float getToughness() {
                return toughness;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        };
    }
}
