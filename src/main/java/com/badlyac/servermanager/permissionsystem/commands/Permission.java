package com.badlyac.servermanager.permissionsystem.commands;

import com.badlyac.servermanager.permissionsystem.PermissionsFile;
import com.badlyac.servermanager.utils.Msg;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
            Msg.sendColoredMsgToCommandSender(context.getSource(),"No players found for the selector: " + playerSelector, ChatFormatting.RED);
            return 0;
        }

        for (ServerPlayer player : players) {
            String playerId = player.getGameProfile().getId().toString();
            PermissionsFile.setPlayerPermission(playerId, permission, value);
            Msg.sendColoredMsgToCommandSender(context.getSource(),"Permission " + permission + " set to " + value + " for player " + player.getName().getString(), ChatFormatting.GREEN);;
        }
        return 1;
    }

    private static int removePermission(CommandContext<CommandSourceStack> context, String playerSelector, String permission) {
        Collection<ServerPlayer> players = getPlayersFromSelector(context, playerSelector);

        if (players.isEmpty()) {
            Msg.sendColoredMsgToCommandSender(context.getSource(),"No players found for the selector: " + playerSelector, ChatFormatting.RED);
            return 0;
        }

        for (ServerPlayer player : players) {
            String playerId = player.getGameProfile().getId().toString();
            PermissionsFile.setPlayerPermission(playerId, permission, false);
            Msg.sendColoredMsgToCommandSender(context.getSource(), "Permission " + permission + " removed from player " + player.getName().getString(), ChatFormatting.GREEN);
        }
        return 1;
    }

    private static Collection<ServerPlayer> getPlayersFromSelector(CommandContext<CommandSourceStack> context, String playerSelector) {
        return context.getSource().getServer().getPlayerList().getPlayers();
    }
}
