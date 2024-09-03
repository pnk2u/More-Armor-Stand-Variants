package de.pnku.mstv_masv;

import de.pnku.mstv_masv.item.MoreArmorStandVariantItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MoreArmorStandVariants implements ModInitializer {

	public static final String MOD_ID = "mstv-masv";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	
	@Override
	public void onInitialize() {
		MoreArmorStandVariantItems.registerArmorStandItems();
	}

	public static ResourceLocation asId(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

}
