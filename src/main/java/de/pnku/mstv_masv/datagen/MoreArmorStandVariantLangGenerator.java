package de.pnku.mstv_masv.datagen;

import de.pnku.mstv_masv.item.MoreArmorStandVariantItem;
import de.pnku.mstv_masv.item.MoreArmorStandVariantItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import org.apache.commons.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class MoreArmorStandVariantLangGenerator extends FabricLanguageProvider {
    public MoreArmorStandVariantLangGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (Item armorStandItem : MoreArmorStandVariantItems.more_armor_stands) {
            String armorStandName = WordUtils.capitalizeFully(((MoreArmorStandVariantItem) armorStandItem).masvWoodType + " Armor Stand");
            translationBuilder.add(armorStandItem, armorStandName);
        }
    }
}
