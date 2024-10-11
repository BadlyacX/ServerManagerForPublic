package com.badlyac.servermanager.permissionsystem.commands;

import com.badlyac.servermanager.permissionsystem.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class Permission {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("permission")
                .then(Commands.literal("set")
                        .then(Commands.argument("player", StringArgumentType.word())
                                .then(Commands.argument("permission", StringArgumentType.word())
                                        .then(Commands.argument("value", BoolArgumentType.bool())
                                                .executes(context -> setPermission(
                                                        context,
                                                        StringArgumentType.getString(context, "player"),
                                                        StringArgumentType.getString(context, "permission"),
                                                        BoolArgumentType.getBool(context, "value")
                                                ))))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", StringArgumentType.word())
                                .then(Commands.argument("permission", StringArgumentType.word())
                                        .executes(context -> removePermission(
                                                context,
                                                StringArgumentType.getString(context, "player"),
                                                StringArgumentType.getString(context, "permission")
                                        ))))));
    }

    private static int setPermission(CommandContext<CommandSourceStack> context, String playerSelector, String permission, boolean value) {
        Collection<ServerPlayer> players = getPlayersFromSelector(context, playerSelector);

        if (players.isEmpty()) {
            context.getSource().sendSystemMessage(Component.literal("No players found for the selector: " + playerSelector));
            return 0;
        }

        for (ServerPlayer player : players) {
            String playerId = player.getGameProfile().getId().toString();
            Files.setPlayerPermission(playerId, permission, value);
            context.getSource().sendSystemMessage(Component.literal("Permission " + permission + " set to " + value + " for player " + player.getName().getString()));
        }
        return 1;
    }

    private static int removePermission(CommandContext<CommandSourceStack> context, String playerSelector, String permission) {
        Collection<ServerPlayer> players = getPlayersFromSelector(context, playerSelector);

        if (players.isEmpty()) {
            context.getSource().sendSystemMessage(Component.literal("No players found for the selector: " + playerSelector));
            return 0;
        }

        for (ServerPlayer player : players) {
            String playerId = player.getGameProfile().getId().toString();
            Files.setPlayerPermission(playerId, permission, false);
            context.getSource().sendSystemMessage(Component.literal("Permission " + permission + " removed from player " + player.getName().getString()));
        }
        return 1;
    }

    private static Collection<ServerPlayer> getPlayersFromSelector(CommandContext<CommandSourceStack> context, String playerSelector) {
        return context.getSource().getServer().getPlayerList().getPlayers();
    }
}
