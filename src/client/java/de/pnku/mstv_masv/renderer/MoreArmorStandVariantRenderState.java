package de.pnku.mstv_masv.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;

@Environment(EnvType.CLIENT)
public class MoreArmorStandVariantRenderState extends ArmorStandRenderState {
    public String armorStandVariant;
}
