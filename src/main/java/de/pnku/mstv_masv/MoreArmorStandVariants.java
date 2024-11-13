package de.pnku.mstv_masv;

import de.pnku.mstv_masv.item.MoreArmorStandVariantItem;
import de.pnku.mstv_masv.item.MoreArmorStandVariantItems;
import de.pnku.mstv_masv.util.IArmorStand;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static de.pnku.mstv_masv.item.MoreArmorStandVariantItems.more_armor_stands;


public class MoreArmorStandVariants implements ModInitializer {

	public static final String MOD_ID = "mstv-masv";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	
	@Override
	public void onInitialize() {
		MoreArmorStandVariantItems.registerArmorStandItems();
		for (Item armorStandItem : more_armor_stands) {
			DispenserBlock.registerBehavior((ItemLike) armorStandItem, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){
				@Override
				public ItemStack execute(BlockSource blockSource, ItemStack item) {
					Consumer<ArmorStand> consumer;
					Direction direction = (Direction)blockSource.state().getValue((Property)DispenserBlock.FACING);
					BlockPos blockPos = blockSource.pos().relative(direction);
					ServerLevel serverLevel = blockSource.level();
					ArmorStand armorStandEntity = EntityType.ARMOR_STAND.spawn(serverLevel, consumer = EntityType.appendDefaultStackConfig(armorStand -> armorStand.setYRot(direction.toYRot()), serverLevel, item, null), blockPos, EntitySpawnReason.DISPENSER, false, false);
					if (armorStandEntity != null) {
						item.shrink(1);
						((IArmorStand) armorStandEntity).masv$setVariant(((MoreArmorStandVariantItem) armorStandItem).masvWoodType);
					}
					return item;
				}
			});
		}
	}

	public static ResourceLocation asId(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

}
