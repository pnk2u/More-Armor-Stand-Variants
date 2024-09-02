package de.pnku.mstv_masv.mixin.client;

import de.pnku.mstv_masv.MoreArmorStandVariants;
import de.pnku.mstv_masv.util.IArmorStand;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandRenderer.class)
public class ArmorStandRendererMixin {

    @Shadow @Final public static ResourceLocation DEFAULT_SKIN_LOCATION;

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/decoration/ArmorStand;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void injectedGetTextureLocation(ArmorStand entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (((IArmorStand) entity).masv$getVariant() != null) {
            if (((IArmorStand) entity).masv$getVariant().equals("oak")) {
                cir.setReturnValue(DEFAULT_SKIN_LOCATION);
            } else {
                String path = "textures/entity/armorstand/" + ((IArmorStand) entity).masv$getVariant() + ".png";
                cir.setReturnValue(new ResourceLocation(MoreArmorStandVariants.MOD_ID, path));
            }
        }
    }
}
