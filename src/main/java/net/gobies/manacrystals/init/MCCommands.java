package net.gobies.manacrystals.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.gobies.manacrystals.ManaCrystals;
import net.gobies.manacrystals.item.ManaCrystalItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = ManaCrystals.MOD_ID)
public class MCCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("manacrystals")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("add")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(context -> modifyCrystals(
                                                context.getSource(),
                                                EntityArgument.getPlayers(context, "targets"),
                                                IntegerArgumentType.getInteger(context, "amount"),
                                                "add"
                                        ))
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(context -> modifyCrystals(
                                                context.getSource(),
                                                EntityArgument.getPlayers(context, "targets"),
                                                IntegerArgumentType.getInteger(context, "amount"),
                                                "remove"
                                        ))
                                )
                        )
                )
                .then(Commands.literal("clear")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(context -> modifyCrystals(
                                        context.getSource(),
                                        EntityArgument.getPlayers(context, "targets"),
                                        0,
                                        "clear"
                                ))
                        )
                )
        );
    }

    private static int modifyCrystals(CommandSourceStack source, Collection<ServerPlayer> targets, int amount, String action) {
        int maxUses = ManaCrystalItem.getMaxUses();
        for (ServerPlayer player : targets) {
            int current = ManaCrystalItem.getManaCrystalUses(player);
            int uses = current;

            if ("add".equals(action)) {
                uses = Math.min(maxUses, current + amount);
            } else if ("remove".equals(action)) {
                uses = Math.max(0, current - amount);
            } else if ("clear".equals(action)) {
                uses = 0;
            }

            player.getPersistentData().putInt(ManaCrystalItem.MANA_CRYSTAL_USES, uses);

            ManaCrystalItem.applyManaBonus(player);

            final int finalUses = uses;
            source.sendSuccess(() -> Component.literal("Mana Crystal count set to " + finalUses + "/" + maxUses), true);
        }
        return targets.size();
    }
}