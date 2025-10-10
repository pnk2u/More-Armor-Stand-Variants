package de.pnku.mstv_masv.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.pnku.mstv_base.item.MoreStickVariantItem;
import de.pnku.mstv_masv.util.IArmorStand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
            case "pale_oak" -> {return new ItemStack(PALE_OAK_ARMOR_STAND);}
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

    @WrapOperation(method = "brokenByPlayer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At(value = "NEW", target = "net/minecraft/world/item/ItemStack"))
    private ItemStack wrappedNewItemStackInBrokenByPlayer(ItemLike item, Operation<ItemStack> original) {
        String armorStandVariant = ((IArmorStand) this).masv$getVariant();
        if (armorStandVariant != null) {
            return armorStandItemFromVariant(armorStandVariant);
        } else {
            return original.call(item);
        }
    }

    @WrapOperation(method = "showBreakingParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState wrappedDefaultBlockStateInShowBreakingParticles(Block inputBlock, Operation<BlockState> original) {
        String armorStandVariant = this.masv$getVariant();
        if (!armorStandVariant.equals("oak") && !armorStandVariant.isEmpty()) {
            Block block = Block.byItem(MoreStickVariantItem.getPlanksItem(armorStandVariant));
            return block.defaultBlockState();
        }
        return original.call(inputBlock);
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
