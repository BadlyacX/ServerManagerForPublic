package com.badlyac.servermanager.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

public class ColoredMsg {

    public static void sendToCommandSender(CommandSourceStack source, String msg, ChatFormatting colors) {
        source.sendFailure(net.minecraft.network.chat.Component.literal(msg).setStyle(Style.EMPTY.withColor(colors)));
    }
    public static void sendToCommandSender(ServerPlayer player, String msg, ChatFormatting colors) {
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal(msg).setStyle(Style.EMPTY.withColor(colors)));
    }
}
