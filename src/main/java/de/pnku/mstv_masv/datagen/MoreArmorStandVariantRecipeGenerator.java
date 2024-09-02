package de.pnku.mstv_masv.datagen;

import de.pnku.mstv_base.item.MoreStickVariantItem;
import de.pnku.mstv_masv.MoreArmorStandVariants;
import de.pnku.mstv_masv.item.MoreArmorStandVariantItem;
import de.pnku.mstv_masv.item.MoreArmorStandVariantItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static de.pnku.mstv_masv.item.MoreArmorStandVariantItems.more_armor_stands;

public class MoreArmorStandVariantRecipeGenerator extends FabricRecipeProvider {
    public MoreArmorStandVariantRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        for (Item armorStandItem : more_armor_stands) {
            String planksWood = ((MoreArmorStandVariantItem) armorStandItem).masvWoodType;
            Item stickVariant = MoreStickVariantItem.getStickItem(planksWood);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, armorStandItem)
                    .group("armor_stands")
                    .unlockedBy("has_stick", has(stickVariant))
                    .define('/', stickVariant)
                    .define('_', Items.SMOOTH_STONE_SLAB)
                    .pattern("///")
                    .pattern(" / ")
                    .pattern("/_/")
                    .save(exporter, MoreArmorStandVariants.asId(planksWood + "_armor_stand"));
        }
    }
}
