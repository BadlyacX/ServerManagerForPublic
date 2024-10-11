package com.badlyac.servermanager.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Style;

import java.awt.*;

public class ColoredString {

    public static void sendToCommandSender(CommandSourceStack source, String msg, ChatFormatting colors) {
        source.sendFailure(net.minecraft.network.chat.Component.literal(msg).setStyle(Style.EMPTY.withColor(colors)));
    }
}
