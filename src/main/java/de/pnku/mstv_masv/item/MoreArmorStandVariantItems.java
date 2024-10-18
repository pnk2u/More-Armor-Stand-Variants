package de.pnku.mstv_masv.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.core.Registry;
import de.pnku.mstv_masv.MoreArmorStandVariants;

import java.util.ArrayList;
import java.util.List;


public class MoreArmorStandVariantItems {

    // Armor Stands
    public static final Item ACACIA_ARMOR_STAND = new MoreArmorStandVariantItem("acacia", new Item.Properties().stacksTo(16));
    public static final Item BAMBOO_ARMOR_STAND = new MoreArmorStandVariantItem("bamboo", new Item.Properties().stacksTo(16));
    public static final Item BIRCH_ARMOR_STAND = new MoreArmorStandVariantItem("birch", new Item.Properties().stacksTo(16));
    public static final Item CHERRY_ARMOR_STAND = new MoreArmorStandVariantItem("cherry", new Item.Properties().stacksTo(16));
    public static final Item CRIMSON_ARMOR_STAND = new MoreArmorStandVariantItem("crimson", new Item.Properties().stacksTo(16).fireResistant());
    public static final Item DARK_OAK_ARMOR_STAND = new MoreArmorStandVariantItem("dark_oak", new Item.Properties().stacksTo(16));
    public static final Item JUNGLE_ARMOR_STAND = new MoreArmorStandVariantItem("jungle", new Item.Properties().stacksTo(16));
    public static final Item MANGROVE_ARMOR_STAND = new MoreArmorStandVariantItem("mangrove", new Item.Properties().stacksTo(16));
    public static final Item SPRUCE_ARMOR_STAND = new MoreArmorStandVariantItem("spruce", new Item.Properties().stacksTo(16));
    public static final Item WARPED_ARMOR_STAND = new MoreArmorStandVariantItem("warped", new Item.Properties().stacksTo(16).fireResistant());

//    public static MoreArmorStandVariantItem createMasvItem(String masvWoodType){
//        if (masvWoodType.matches("crimson|warped")) {
//            return new MoreArmorStandVariantItem(masvWoodType, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, MoreArmorStandVariants.asId(masvWoodType + "_armor_stand")))
//                    .stacksTo(16).fireResistant());
//        } else {
//        return new MoreArmorStandVariantItem(masvWoodType, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, MoreArmorStandVariants.asId(masvWoodType + "_armor_stand")))
//                .stacksTo(16));
//        }
//    }

    public static final List<Item> more_armor_stands = new ArrayList<>();

    public static void registerArmorStandItems() {
        registerArmorStandItem(ACACIA_ARMOR_STAND, Items.ARMOR_STAND);
        registerArmorStandItem(BAMBOO_ARMOR_STAND, ACACIA_ARMOR_STAND);
        registerArmorStandItem(BIRCH_ARMOR_STAND, BAMBOO_ARMOR_STAND);
        registerArmorStandItem(CHERRY_ARMOR_STAND, BIRCH_ARMOR_STAND);
        registerArmorStandItem(CRIMSON_ARMOR_STAND, CHERRY_ARMOR_STAND);
        registerArmorStandItem(DARK_OAK_ARMOR_STAND, CRIMSON_ARMOR_STAND);
        registerArmorStandItem(JUNGLE_ARMOR_STAND, DARK_OAK_ARMOR_STAND);
        registerArmorStandItem(MANGROVE_ARMOR_STAND, JUNGLE_ARMOR_STAND);
        registerArmorStandItem(SPRUCE_ARMOR_STAND, MANGROVE_ARMOR_STAND);
        registerArmorStandItem(WARPED_ARMOR_STAND, SPRUCE_ARMOR_STAND);
    }

    private static void registerArmorStandItem(Item armorStandItem, Item armorStandAfter) {
        String armorStandName = ((MoreArmorStandVariantItem) armorStandItem).masvWoodType + "_armor_stand";
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(MoreArmorStandVariants.MOD_ID, armorStandName), armorStandItem);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.addAfter(armorStandAfter, armorStandItem));
        more_armor_stands.add(armorStandItem);
        MoreArmorStandVariants.LOGGER.info("Registered: " + armorStandName);
    }
    
}
