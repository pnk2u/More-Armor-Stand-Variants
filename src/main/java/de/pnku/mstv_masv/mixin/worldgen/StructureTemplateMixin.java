package de.pnku.mstv_masv.mixin.worldgen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.pnku.mstv_masv.MoreArmorStandVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    @WrapOperation(method = "createEntityIgnoreException", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private static Optional<Entity> wrappedCreateEntityIgnoreException(CompoundTag nbt, Level level, Operation<Optional<Entity>> original, @Local(ordinal = 0, argsOnly = true) ServerLevelAccessor serverLevel) {
        MoreArmorStandVariants.LOGGER.info("Creating entity with ID: " + nbt.getString("id"));
        if (nbt.getString("id").equals("minecraft:armor_stand")) {
            MoreArmorStandVariants.LOGGER.info("Creating armor stand at position: " + nbt.getList("Pos", 6));
            ListTag posList = nbt.getList("Pos", 6);
            BlockPos pos = new BlockPos((int) posList.getDouble(0), (int) posList.getDouble(1), (int) posList.getDouble(2));
            MoreArmorStandVariants.LOGGER.info("Created position: " + pos);
            String biomeName = serverLevel.getBiome(pos).getRegisteredName();
            MoreArmorStandVariants.LOGGER.info("Determined biome: " + biomeName + " at position: " + pos);
            String woodType;
            switch (biomeName) {
                case "minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_savanna" -> woodType = "acacia";
                case "minecraft:birch_forest", "minecraft:old_growth_birch_forest", "minecraft:forest", "minecraft:meadow" -> woodType = "birch";
                case "minecraft:bamboo_jungle" -> woodType = "bamboo";
                case "minecraft:jungle", "minecraft:sparse_jungle", "minecraft:desert" -> woodType = "jungle";
                case "minecraft:cherry_grove" -> woodType = "cherry";
                case "minecraft:crimson_forest", "minecraft:nether_wastes" -> woodType = "crimson";
                case "minecraft:dark_forest" -> woodType = "dark_oak";
                case "minecraft:pale_garden" -> woodType = "pale_oak";
                case "minecraft:mangrove_swamp" -> woodType = "mangrove";
                case "minecraft:old_growth_spruce_taiga", "minecraft:old_growth_pine_taiga", "minecraft:taiga", "minecraft:snowy_taiga",
                     "minecraft:windswept_forest", "minecraft:snowy_plains" -> woodType = "spruce";
                case "minecraft:warped_forest", "minecraft:soul_sand_valley" -> woodType = "warped";
                default -> woodType = "oak";
            }
            CompoundTag nbtWithVariant = nbt.copy();
            MoreArmorStandVariants.LOGGER.info("Determined wood type: " + woodType + " for biome: " + biomeName);
            nbtWithVariant.putString("Type", woodType);
            return original.call(nbtWithVariant, level);
        }
        return original.call(nbt, level);
    }
}

