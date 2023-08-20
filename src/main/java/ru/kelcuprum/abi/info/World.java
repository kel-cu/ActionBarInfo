package ru.kelcuprum.abi.info;

import net.minecraft.client.Minecraft;
import ru.kelcuprum.abi.config.Localization;

public class World {
    public static String getTime(){
        Minecraft CLIENT = Minecraft.getInstance();
        if(CLIENT.level == null) return "";
        long currentTime = CLIENT.level.getDayTime() % 24000;
        if (currentTime < 6000 && currentTime > 0) {
            return Localization.getLocalization("time.morning", false);
        } else if (currentTime < 12000 && currentTime > 6000) {
            return Localization.getLocalization("time.day", false);
        } else if (currentTime < 16500 && currentTime > 12000) {
            return Localization.getLocalization("time.evening", false);
        } else if (currentTime > 16500) {
            return Localization.getLocalization("time.night", false);
        } else {
            return "";
        }
    }
    public static String getCodeName(){
        Minecraft CLIENT = Minecraft.getInstance();
        return CLIENT.level.dimensionTypeRegistration().value().toString();//getRegistryKey().getValue().toString();
    }
    public static String getName(){
        String world = getCodeName();
        if(world.equals("minecraft:the_moon")) return Localization.getLocalization("world.moon", false);
        if(world.equals("minecraft:the_end")) return Localization.getLocalization("world.the_end", false);
        if(world.equals("minecraft:the_nether")) return Localization.getLocalization("world.nether", false);
        if(world.equals("minecraft:overworld")) return Localization.getLocalization("world.overworld", false);
        return Localization.getLocalization("world.unknown", false);
    }
}
