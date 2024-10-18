package de.pnku.mstv_masv.mixin.entity;

import de.pnku.mstv_masv.item.MoreArmorStandVariantItems;
import de.pnku.mstv_masv.util.IArmorStand;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// import static de.pnku.mstv_masv.MoreArmorStandVariants.LOGGER;
import static de.pnku.mstv_masv.item.MoreArmorStandVariantItems.*;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends LivingEntity implements IArmorStand {

    @Unique
    private static final EntityDataAccessor<String> DATA_ID_TYPE;

    protected ArmorStandMixin(EntityType<? extends ArmorStand> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_ID_TYPE, "oak");
    }

    @Unique
    public void masv$setVariant(String woodVariant) {
        this.entityData.set(DATA_ID_TYPE, woodVariant);
    }

    @Unique
    public String masv$getVariant() {
        return this.entityData.get(DATA_ID_TYPE);
    }

    @Unique
    public ItemStack armorStandItemFromVariant(String variant) {
        switch (variant) {
            case "oak" -> {return new ItemStack(Items.ARMOR_STAND);}
            case "acacia" -> {return new ItemStack(ACACIA_ARMOR_STAND);}
            case "bamboo" -> {return new ItemStack(BAMBOO_ARMOR_STAND);}
            case "birch" -> {return new ItemStack(BIRCH_ARMOR_STAND);}
            case "cherry" -> {return new ItemStack(CHERRY_ARMOR_STAND);}
            case "crimson" -> {return new ItemStack(CRIMSON_ARMOR_STAND);}
            case "dark_oak" -> {return new ItemStack(DARK_OAK_ARMOR_STAND);}
            case "jungle" -> {return new ItemStack(JUNGLE_ARMOR_STAND);}
            case "mangrove" -> {return new ItemStack(MANGROVE_ARMOR_STAND);}
            case "spruce" -> {return new ItemStack(SPRUCE_ARMOR_STAND);}
            case "warped" -> {return new ItemStack(WARPED_ARMOR_STAND);}
            case null, default -> {return new ItemStack(Items.ARMOR_STAND);}
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    protected void injectedAddAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putString("Type", this.masv$getVariant());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    protected void injectedReadAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("Type", 8)) {
            this.masv$setVariant(compound.getString("Type"));
        }
    }

    @Inject(method = "brokenByPlayer", at = @At("HEAD"), cancellable = true)
    private void injectedBrokenByPlayer(DamageSource damageSource, CallbackInfo ci) {
        String armorStandVariant = ((IArmorStand) this).masv$getVariant();
        if (armorStandVariant != null) {
            // debug
            // LOGGER.info("Armor Stand Variant found: {}", (armorStandVariant));

            ItemStack itemStack = armorStandItemFromVariant(armorStandVariant);
        itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        Block.popResource(this.level(), this.blockPosition(), itemStack);
        ((ArmorStand) (Object) this).brokenByAnything(damageSource);
        }
        ci.cancel();
    }

    @Inject(method = "getPickResult", at = @At("HEAD"), cancellable = true)
    public void getPickResult(CallbackInfoReturnable<ItemStack> cir) {
        String armorStandVariant = ((IArmorStand) this).masv$getVariant();
        if (armorStandVariant != null) {
            cir.setReturnValue(armorStandItemFromVariant(armorStandVariant));
        }
    }

    static {
        DATA_ID_TYPE = SynchedEntityData.defineId(ArmorStandMixin.class, EntityDataSerializers.STRING);
    }

}
