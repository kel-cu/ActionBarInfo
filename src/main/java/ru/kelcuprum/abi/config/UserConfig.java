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
     * 0 - Стандартный майнкрафта
     * 1 - Чуть выше стандартной функции ActionBar в MC
     * 2 - down left
     * 3 - down right
     * 4 - up left
     * 5 - up right
     */
    public static int TYPE_RENDER_ACTION_BAR = 0;
    public static int INDENT_X = 20;
    public static int INDENT_Y = 20;
    public static boolean EXPERIMENT = false;
    public static boolean RENDER_IN_DEBUG_SCREEN = false;

    /**
     * Сохранение конфигурации
     */
    public static void save(){
        Minecraft mc = Minecraft.getInstance();
        final Path configFile = mc.gameDirectory.toPath().resolve("config/ActionBarInfo/config.json");
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("ENABLE_AB_INFORMATION", ENABLE_AB_INFORMATION)
                .put("VIEW_ITEM_OFF_HAND", VIEW_ITEM_OFF_HAND)
                .put("USE_EXTENDED_COORDINATES", USE_EXTENDED_COORDINATES)
                .put("TYPE_RENDER_ACTION_BAR", TYPE_RENDER_ACTION_BAR)
                .put("INDENT_X", INDENT_X)
                .put("INDENT_Y", INDENT_Y)
                .put("RENDER_IN_DEBUG_SCREEN", RENDER_IN_DEBUG_SCREEN)
                .put("EXPERIMENT", EXPERIMENT);
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
            for(String key : jsonConfig.keySet()){
                switch (key){
                    case "ENABLE_AB_INFORMATION" -> ENABLE_AB_INFORMATION = jsonConfig.getBoolean(key);
                    case "VIEW_ITEM_OFF_HAND" -> VIEW_ITEM_OFF_HAND = jsonConfig.getBoolean(key);
                    case "USE_EXTENDED_COORDINATES" -> USE_EXTENDED_COORDINATES = jsonConfig.getBoolean(key);
                    case "TYPE_RENDER_ACTION_BAR" -> TYPE_RENDER_ACTION_BAR = jsonConfig.getInt(key);
                    case "INDENT_X" -> INDENT_X = jsonConfig.getInt(key);
                    case "INDENT_Y" -> INDENT_Y = jsonConfig.getInt(key);
                    case "EXPERIMENT" -> EXPERIMENT = jsonConfig.getBoolean(key);
                    case "RENDER_IN_DEBUG_SCREEN" -> RENDER_IN_DEBUG_SCREEN = jsonConfig.getBoolean(key);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            save();
        }

    }
}