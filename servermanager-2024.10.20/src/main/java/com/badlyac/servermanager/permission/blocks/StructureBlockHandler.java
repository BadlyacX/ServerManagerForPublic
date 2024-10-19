package com.badlyac.servermanager.permission.blocks;

import com.badlyac.servermanager.Main;
import com.badlyac.servermanager.permission.PermissionManager;
import com.badlyac.servermanager.utils.Msg;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class StructureBlockHandler {
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.getLevel().getBlockState(event.getPos()).getBlock() instanceof StructureBlock) {
            if (!PermissionManager.hasPerm(player, "minecraft.blocks.structureblock") || !PermissionManager.hasPerm(player, "minecraft.blocks.super")) {
                event.setCanceled(true);

                Msg.sendColoredMsgToPlayer(player,"You do not have permission to use structureblock!", ChatFormatting.RED);
            }
        }
    }
}
