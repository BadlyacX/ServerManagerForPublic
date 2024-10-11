package com.badlyac.servermanager.permissionsystem;

import com.badlyac.servermanager.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class CommandInterceptor {

    @SubscribeEvent
    public static void onCommandEvent(CommandEvent event) {
        CommandSourceStack source = event.getParseResults().getContext().getSource();

        if (source.getEntity() instanceof ServerPlayer player) {
            String playerId = player.getGameProfile().getId().toString();

            String command = event.getParseResults().getReader().getString().split(" ")[0];
            String permissionName = "command." + command;

            if (!Files.hasPermission(playerId, permissionName)) {
                source.sendFailure(Component.literal("You do not have permission to use this command.").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                event.setCanceled(true);
            }
        }
    }
}
