package com.badlyac.servermanager.permissionsystem;

import com.badlyac.servermanager.Main;
import com.badlyac.servermanager.utils.ColoredMsg;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
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

            if (!Files.hasPermission(playerId, permissionName) || !Files.hasPermission(playerId, "command.*")) {
                ColoredMsg.sendToCommandSender(source,"You do not have permission to use this command.", ChatFormatting.RED);
                event.setCanceled(true);
            }
        }
    }
}
