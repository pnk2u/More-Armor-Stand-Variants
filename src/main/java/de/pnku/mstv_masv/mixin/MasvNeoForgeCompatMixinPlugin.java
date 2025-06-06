package de.pnku.mstv_masv.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static de.pnku.mstv_masv.MoreArmorStandVariants.LOGGER;

public class MasvNeoForgeCompatMixinPlugin implements IMixinConfigPlugin {
    public static boolean isNeoForge = false;
    @Override
    public void onLoad(String mixinPackage) {
        isNeoForge = FabricLoader.getInstance().isModLoaded("neoforge");
        if (isNeoForge) LOGGER.info("Detected Neoforge environment. Did not apply StructureTemplateMixin.");
        else LOGGER.debug("Detected Fabric environment, applying StructureTemplateMixin.");
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !isNeoForge;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
