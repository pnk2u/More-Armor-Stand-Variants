package de.pnku.mstv_masv.mixin.worldgen;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.pnku.mstv_masv.MoreArmorStandVariants.LOGGER;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {

    @Inject(method = "placeEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;"))
    private void injectedPlaceEntities(ServerLevelAccessor serverLevel, BlockPos pos, Mirror mirror, Rotation rotation, BlockPos offset, BoundingBox boundingBox, boolean withEntities, CallbackInfo ci, @Local(ordinal = 0) LocalRef<CompoundTag> nbt) {
            if (nbt.get().getString("id").equals("minecraft:armor_stand")) {
                String biomeName = serverLevel.getBiome(pos).unwrapKey().get().location().toString();
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
                CompoundTag nbtWithVariant = nbt.get();
                nbtWithVariant.putString("Type", woodType);
                nbt.set(nbtWithVariant);
            }
    }
}

