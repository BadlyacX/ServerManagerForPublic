package com.badlyac.servermanager.permissionsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
    private static final File PERMISSIONS_FILE = new File("permissions.json");
    private static JsonObject permissionsData;

    public static void initConfig() {
        try {
            if (!PERMISSIONS_FILE.exists()) {
                PERMISSIONS_FILE.createNewFile();
                FileWriter writer = new FileWriter(PERMISSIONS_FILE);
                writer.write("{}");
                writer.close();
            }

            FileReader reader = new FileReader(PERMISSIONS_FILE);
            permissionsData = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPlayerPermission(String playerId, String commandName, boolean value) {
        String permissionKey = "command." + commandName;
        permissionsData.addProperty(playerId + "." + permissionKey, value);
        savePermissions();
    }

    public static boolean hasPermission(String playerId, String commandName) {
        String permissionKey = "command." + commandName;
        return permissionsData.has(playerId + "." + permissionKey) && permissionsData.get(playerId + "." + permissionKey).getAsBoolean();
    }

    private static void savePermissions() {
        try (FileWriter writer = new FileWriter(PERMISSIONS_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(permissionsData, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
