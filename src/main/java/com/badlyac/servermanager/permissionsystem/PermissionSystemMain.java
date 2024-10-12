package com.badlyac.servermanager.permissionsystem;

import com.badlyac.servermanager.Main;
import com.badlyac.servermanager.permissionsystem.commands.Permission;
import com.badlyac.servermanager.permissionsystem.commands.Rank;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.processing.SupportedSourceVersion;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class PermissionSystemMain {

    public static void initializePermissionSystem() {
        PermissionsFile.initPermissionsFile();
        RanksFile.initRanksFile();
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        Permission.register(commandDispatcher);
        Rank.register(commandDispatcher);
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        initializePermissionSystem();
    }
}
