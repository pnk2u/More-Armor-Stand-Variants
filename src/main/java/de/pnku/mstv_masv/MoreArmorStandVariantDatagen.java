package de.pnku.mstv_masv;

import de.pnku.mstv_masv.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public class MoreArmorStandVariantDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		//pack.addProvider(MoreArmorStandVariantRecipeGenerator::new);
		//pack.addProvider(MoreArmorStandVariantLangGenerator::new);
    }
}
