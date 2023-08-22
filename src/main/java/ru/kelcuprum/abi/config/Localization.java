package ru.kelcuprum.abi.config;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.json.JSONObject;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.info.Player;
import ru.kelcuprum.abi.info.Utils;
import ru.kelcuprum.abi.info.World;
import ru.kelcuprum.abi.mixin.MinecraftAccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Localization {
    /**
     * Получение кода локализации игры который выбрал игрок<br>
     * При запуске может быть null, поэтому иногда en_us;
     */
    public static String getCodeLocalization(){
        Minecraft CLIENT = Minecraft.getInstance();
        try{
//            return CLIENT.getGame().getSelectedLanguage().getCode();
            return CLIENT.options.languageCode;
        } catch (Exception e){
            return "en_us";
        }
    }

    /**
     * Получение JSON файл локализации
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject getJSONFile() throws IOException {
        Minecraft CLIENT = Minecraft.getInstance();
        File localizationFile = new File(CLIENT.gameDirectory + "/config/ActionBarInfo/lang/"+getCodeLocalization()+".json");
        if(localizationFile.exists()){
            return new JSONObject(Files.readString(localizationFile.toPath()));
        } else {
            return new JSONObject();
        }
    }

    /**
     * Получение текста локализации
     * @param type
     * @param parse
     * @return String
     */
    public static String getLocalization(String type, boolean parse){
        return getLocalization(type, parse, false);
    }
    public static String getLocalization(String type, boolean parse, boolean clearColor){
        String text = "";
        try {
            JSONObject JSONLocalization = getJSONFile();
            if(JSONLocalization.isNull(type)) text = getText("abi." + type).getString();
            else text = JSONLocalization.getString(type);
        } catch (Exception e){
            e.printStackTrace();
            text = getText("abi." + type).getString();
        }
        if(parse) return getParsedText(text, clearColor);
        else return text;
    }
    public static String getLcnDefault(String type){
        String text = getText("abi." + type).getString();;
        return text;
    }
    /**
     * Задать значение локализации на определённый текст в JSON файле
     * @param type
     * @param text
     */
    public static void setLocalization(String type, String text){
        try {
            JSONObject JSONLocalization = getJSONFile();
            JSONLocalization.put(type, text);
            Minecraft CLIENT = Minecraft.getInstance();
            File localizationFile = new File(CLIENT.gameDirectory + "/config/ActionBarInfo/lang/"+getCodeLocalization()+".json");
            Files.createDirectories(localizationFile.toPath().getParent());
            Files.writeString(localizationFile.toPath(), JSONLocalization.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Хуета которая может быть спасёт от Mojang которые сука постоянно меняют либо название класса либо еще что-то
     * @return MutableText
     * @param key
     */
    public static Component getText(String key){
        return Component.translatable(key);
    }

    /**
     * Перевод String в MutableText
     * @param text
     * @return MutableText
     */
    public static Component toText(String text){
        return Component.literal(text);
    }

    /**
     * Перевод Text в String
     * @param text
     * @return MutableText
     */
    public static String toString(Component text){
        return text.getString();
    }


    /**
     * Парс текста
     * @param text
     * @return String
     */
    public static String getParsedText(String text, boolean clearColor){
        String parsedText = text;
        Minecraft CLIENT = Minecraft.getInstance();
        parsedText = parsedText.replace("%version%", CLIENT.getLaunchedVersion());
        parsedText = parsedText.replace("%modded%", ClientBrandRetriever.getClientModName());
        parsedText = parsedText.replace("%version_type%", ("release".equalsIgnoreCase(CLIENT.getVersionType()) ? "" : CLIENT.getVersionType()));
        parsedText = parsedText.replace("%name%", CLIENT.getUser().getName());
        if(CLIENT.level != null && CLIENT.player != null){
            if(Player.getItemName() == null) parsedText = parsedText.replace("%item%", "");
            parsedText = parsedText.replace("%item%", getLocalization("item.format", false));
            parsedText = parsedText.replace("%item_name%", Player.getItemName()+"");
            if(Player.getItemCount() >= 2) {
                parsedText = parsedText.replace("%item_pcs%", getLocalization("item.format.count", false));
                parsedText = parsedText.replace("%item_count%", Player.getItemCount() + "");
            } else {
                parsedText = parsedText.replace("%item_pcs%", "");
                parsedText = parsedText.replace("%item_count%", "");
            }
            parsedText = parsedText.replace("%x%", Player.getX());
            parsedText = parsedText.replace("%y%", Player.getY());
            parsedText = parsedText.replace("%z%", Player.getZ());
            parsedText = parsedText.replace("%sirection%", "%direction%");
            parsedText = parsedText.replace("%sirectionSymbol%", "%directionSymbol%");
            parsedText = parsedText.replace("%direction%", Player.getDirection(false));
            parsedText = parsedText.replace("%directionSymbol%", Player.getDirection(true));
            if(!CLIENT.isSingleplayer() && CLIENT.getCurrentServer() != null){
                parsedText = parsedText.replace("%scene%", "%address%");
                if(ServerConfig.SHOW_ADDRESS){
                    if(ServerConfig.SHOW_CUSTOM_NAME) parsedText = parsedText.replace("%address%", ServerConfig.CUSTOM_NAME);
                    else if(ServerConfig.SHOW_NAME_IN_LIST) parsedText = parsedText.replace("%address%", CLIENT.getCurrentServer().name);
                    else parsedText = parsedText.replace("%address%", CLIENT.getCurrentServer().ip);
                } else parsedText = parsedText.replace("%address%", getLocalization("address.hidden", false));
            } else parsedText = parsedText.replace("%scene%", getLocalization("singleplayer", false));
            parsedText = parsedText.replace("%health%", Player.getHealth());
            parsedText = parsedText.replace("%health_max%", Player.getMaxHealth());
            parsedText = parsedText.replace("%health_percent%", Player.getPercentHealth());
            parsedText = parsedText.replace("%armor%", Player.getArmor());
            parsedText = parsedText.replace("%xp%", String.valueOf(CLIENT.player.experienceLevel));
            parsedText = parsedText.replace("%gamma%", ActionBarInfo.DF.format(CLIENT.options.gamma().get()*100));
            parsedText = parsedText.replace("%sps%", "%fps%");
            parsedText = parsedText.replace("%fps%", String.valueOf(MinecraftAccess.getFPS()));
            parsedText = parsedText.replace("%world%", World.getName());
            parsedText = parsedText.replace("%world_time_type%", World.getTimeType());
            parsedText = parsedText.replace("%world_time%", World.getTime());
            parsedText = parsedText.replace("%time_type%", World.getTimeType());
            parsedText = parsedText.replace("%time%", World.getTime());
        }
        try{
            DateFormat dateFormat = new SimpleDateFormat(Localization.getLocalization("date", false));
            DateFormat timeFormat = new SimpleDateFormat(Localization.getLocalization("date.time", false));
            parsedText = parsedText.replace("%sate%", "%date%");
            parsedText = parsedText.replace("%date%", Localization.getLocalization("date.format", false));
            parsedText = parsedText.replace("%sate_format%", "%date_format%");
            parsedText = parsedText.replace("%date_format%", dateFormat.format(System.currentTimeMillis()));
            parsedText = parsedText.replace("%time_format%", timeFormat.format(System.currentTimeMillis()));
        } catch (Exception e) {

        }

        if(clearColor) parsedText = Utils.clearFormatCodes(parsedText);
        else parsedText = Utils.fixFormatCodes(parsedText);
        return parsedText;
    }
}
