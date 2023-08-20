package ru.kelcuprum.abi.config;

import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserConfig {
    public static boolean ENABLE_AB_INFORMATION = true;
    public static boolean VIEW_ITEM_OFF_HAND = false;

    public static boolean USE_EXTENDED_COORDINATES = false;

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        Minecraft mc = Minecraft.getInstance();
        final Path configFile = mc.gameDirectory.toPath().resolve("config/ActionBarInfo/config.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("ENABLE_AB_INFORMATION", ENABLE_AB_INFORMATION)
                .put("VIEW_ITEM_OFF_HAND", VIEW_ITEM_OFF_HAND)
                .put("USE_EXTENDED_COORDINATES", USE_EXTENDED_COORDINATES);
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, jsonConfig.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка файла конфигов
     */
    public static void load(){
        Minecraft mc = Minecraft.getInstance();
        final Path configFile = mc.gameDirectory.toPath().resolve("config/ActionBarInfo/config.json");
        try{
            JSONObject jsonConfig = new JSONObject(Files.readString(configFile));
            if(!jsonConfig.isNull("ENABLE_AB_INFORMATION")) ENABLE_AB_INFORMATION = jsonConfig.getBoolean("ENABLE_AB_INFORMATION");
            else ENABLE_AB_INFORMATION = true;
            if(!jsonConfig.isNull("VIEW_ITEM_OFF_HAND")) VIEW_ITEM_OFF_HAND = jsonConfig.getBoolean("VIEW_ITEM_OFF_HAND");
            else VIEW_ITEM_OFF_HAND = false;
            if(!jsonConfig.isNull("USE_EXTENDED_COORDINATES")) USE_EXTENDED_COORDINATES = jsonConfig.getBoolean("USE_EXTENDED_COORDINATES");
            else USE_EXTENDED_COORDINATES = false;
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}