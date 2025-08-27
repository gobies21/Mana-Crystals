package net.gobies.manacrystals.item;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.gobies.manacrystals.CommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ManaCrystalItem extends Item {
    public ManaCrystalItem(Properties properties) {
        super(properties);
    }

    private static final String USE_COUNT_TAG = "ManaCrystalUseCount";
    public static final String GLOBAL_USE_COUNT_TAG = "GlobalManaCrystalUseCount";
    private static final long COOLDOWN_DURATION = 20;
    private static final String LAST_USE_TAG = "ManaCrystalLastUseTime";

    private static int maxUses;
    public static int getMaxUses() {
        if (maxUses == 0) {
            maxUses = CommonConfig.MANA_CRYSTAL_MAX_USES.get();
        }
        return maxUses;
    }

    public static double getManaIncrease() {
        return CommonConfig.MANA_CRYSTAL_MANA_INCREASE.get();
    }

    public @Nonnull InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand handIn) {
        ItemStack stack = player.getItemInHand(handIn);
        CompoundTag stackTag = stack.getOrCreateTag();

        int stackUseCount = stackTag.getInt(USE_COUNT_TAG);

        CompoundTag playerData = player.getPersistentData();
        CompoundTag modData = playerData.getCompound("ManaCrystalModData");
        int globalUseCount = modData.getInt(GLOBAL_USE_COUNT_TAG);
        long lastUseTime = modData.getLong(LAST_USE_TAG);

        long currentTime = level.getGameTime();
        if (currentTime - lastUseTime < COOLDOWN_DURATION) {
            return InteractionResultHolder.fail(stack);
        }

        if (globalUseCount < getMaxUses() && !level.isClientSide) {
            globalUseCount++;
            modData.putInt(GLOBAL_USE_COUNT_TAG, globalUseCount);
            playerData.put("ManaCrystalModData", modData);
            lastUseTime = currentTime;
            modData.putLong(LAST_USE_TAG, lastUseTime);

            UUID uniqueUUID = UUID.nameUUIDFromBytes(("ManaCrystalManaIncrease" + globalUseCount).getBytes());

            Objects.requireNonNull(player.getAttributes().getInstance(AttributeRegistry.MAX_MANA.get())).addPermanentModifier(
                    new AttributeModifier(
                            uniqueUUID,
                            "ManaCrystalManaIncrease",
                            getManaIncrease(),
                            AttributeModifier.Operation.ADDITION
                    )
            );

            if (stackUseCount < getMaxUses()) {
                stackUseCount++;
                stackTag.putInt(USE_COUNT_TAG, stackUseCount);
            }

            SoundEvent manaCrystalUse = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("manacrystals:mana_crystal_use"));
            if (manaCrystalUse != null) {
                level.playSound(null, player.blockPosition(), manaCrystalUse, SoundSource.PLAYERS, 1.0F, 1.0F);
                stack.shrink(1);

                return InteractionResultHolder.success(stack);
            } else if (globalUseCount >= getMaxUses()) {
                player.displayClientMessage(Component.translatable("message.manacrystals.max_uses"), true);
                return InteractionResultHolder.fail(stack);
            }
        }

        return InteractionResultHolder.fail(player.getItemInHand(handIn));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.manacrystal.item"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}