package ru.kelcuprum.abi.info;

import net.minecraft.client.Minecraft;
import ru.kelcuprum.abi.config.Localization;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class World {
    public static String getTimeType(){
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
    public static String getTime(){
        Minecraft CLIENT = Minecraft.getInstance();
        long daytime = CLIENT.level.getDayTime()+6000;

        int hours=(int) (daytime / 1000)%24;
        int minutes = (int) ((daytime % 1000)*60/1000);
        int day = (int) daytime / 1000 / 24;
        String clock;
        try {
            String strDateFormat = Localization.getLocalization("date.time", false);
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            Calendar calendar = new GregorianCalendar();
            calendar.set(2000, 0, day+1, hours, minutes, 0);

            clock = dateFormat.format(calendar.getTimeInMillis());
        } catch (IllegalArgumentException ex) {
            clock = "illegal clock format; google for Java SimpleDateFormat";
        }
        return clock;

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
