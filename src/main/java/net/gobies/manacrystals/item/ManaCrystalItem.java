package net.gobies.manacrystals.item;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.gobies.manacrystals.CommonConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import java.util.UUID;

public class ManaCrystalItem extends Item {
    public ManaCrystalItem(Properties properties) {
        super(properties);
    }

    private static int maxUses;
    public static final UUID MANA_CRYSTAL_UUID = UUID.fromString("8db4a3b1-0f4a-4632-9df7-111112345678");
    public static final String MANA_CRYSTAL_USES = "ManaCrystalUses";

    public static int getMaxUses() {
        if (maxUses == 0) {
            maxUses = CommonConfig.MANA_CRYSTAL_MAX_USES.get();
        }
        return maxUses;
    }

    public static double getManaIncrease() {
        return CommonConfig.MANA_CRYSTAL_MANA_INCREASE.get();
    }

    public static int getManaCrystalUses(Player player) {
        return player.getPersistentData().getInt(MANA_CRYSTAL_USES);
    }
    public static void applyManaBonus(ServerPlayer player) {
        var attributeInstance = player.getAttribute(AttributeRegistry.MAX_MANA.get());
        if (attributeInstance == null) {
            return;
        }
        int crystalUses = getManaCrystalUses(player);
        double maxManaGain = crystalUses * getManaIncrease();
        AttributeModifier existing = attributeInstance.getModifier(MANA_CRYSTAL_UUID);
        if (existing != null) {
            if (existing.getAmount() == maxManaGain) {
                return;
            }
            attributeInstance.removeModifier(existing);
        }

        if (maxManaGain > 0) {
            attributeInstance.addPermanentModifier(new AttributeModifier(MANA_CRYSTAL_UUID, "manacrystals_bonus", maxManaGain, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public @Nonnull InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand handIn) {
        ItemStack stack = player.getItemInHand(handIn);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            int currentUses = getManaCrystalUses(serverPlayer);
            int maxUsesLimit = getMaxUses();

            if (currentUses < maxUsesLimit) {
                int totalUses = currentUses + 1;
                serverPlayer.getPersistentData().putInt(MANA_CRYSTAL_USES, totalUses);

                applyManaBonus(serverPlayer);

                SoundEvent manaCrystalUse = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("manacrystals:mana_crystal_use"));
                if (manaCrystalUse != null) {
                    level.playSound(null, serverPlayer.blockPosition(), manaCrystalUse, SoundSource.PLAYERS, 1.0F, 1.0F);
                }

                stack.shrink(1);
                return InteractionResultHolder.success(stack);
            } else {
                serverPlayer.displayClientMessage(Component.translatable("message.manacrystals.max_uses"), true);
                return InteractionResultHolder.fail(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.manacrystal.item"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}