package de.pnku.mstv_masv.mixin.client;

import de.pnku.mstv_masv.MoreArmorStandVariants;
import de.pnku.mstv_masv.renderer.MoreArmorStandVariantRenderState;
import de.pnku.mstv_masv.util.IArmorStand;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin extends LivingEntityRenderer<ArmorStand, MoreArmorStandVariantRenderState, ArmorStandArmorModel> implements IArmorStand {

    @Shadow @Final public static ResourceLocation DEFAULT_SKIN_LOCATION;

    public ArmorStandRendererMixin(EntityRendererProvider.Context context, ArmorStandArmorModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "createRenderState()Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;", at = @At("HEAD"), cancellable = true)
    public void injectedCreateRenderState(CallbackInfoReturnable<MoreArmorStandVariantRenderState> cir) {
        cir.setReturnValue(new MoreArmorStandVariantRenderState());
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void injectedGetTextureLocation(ArmorStandRenderState armorStandRenderState, CallbackInfoReturnable<ResourceLocation> cir) {
        MoreArmorStandVariantRenderState moreArmorStandVariantRenderState = (MoreArmorStandVariantRenderState) armorStandRenderState;
        if (moreArmorStandVariantRenderState.armorStandVariant != null) {
            if (moreArmorStandVariantRenderState.armorStandVariant.equals("oak")) {
                cir.setReturnValue(DEFAULT_SKIN_LOCATION);
            } else {
                String path = "textures/entity/armorstand/" + moreArmorStandVariantRenderState.armorStandVariant + ".png";
                cir.setReturnValue(MoreArmorStandVariants.asId(path));
            }
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ArmorStand;Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;F)V", at = @At("HEAD"))
    public void injectedExtractRenderState(ArmorStand armorStand, ArmorStandRenderState armorStandRenderState, float f, CallbackInfo ci){
        MoreArmorStandVariantRenderState moreArmorStandVariantRenderState = (MoreArmorStandVariantRenderState) armorStandRenderState;
        super.extractRenderState(armorStand, moreArmorStandVariantRenderState, f);
        moreArmorStandVariantRenderState.armorStandVariant = ((IArmorStand) armorStand).masv$getVariant();
    }
}
