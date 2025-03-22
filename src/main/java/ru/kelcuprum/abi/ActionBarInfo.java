package ru.kelcuprum.abi;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import org.meteordev.starscript.value.Value;
import org.meteordev.starscript.value.ValueMap;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.api.KeyMappingHelper;
import ru.kelcuprum.alinlib.api.events.alinlib.AlinLibEvents;
import ru.kelcuprum.alinlib.api.events.alinlib.LocalizationEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientLifecycleEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientTickEvents;
import ru.kelcuprum.alinlib.api.events.client.GuiRenderEvents;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.config.Localization;

import static net.minecraft.world.item.Items.COMPASS;

public class ActionBarInfo implements net.fabricmc.api.ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("Action Bar Info");
    private static final Timer TIMER = new Timer();
    private static String lastException;

    public static void log(String message) {
        log(message, Level.INFO);
    }

    public static void log(String message, Level level) {
        LOG.log(level, "[" + LOG.getName() + "] " + message);
    }

    public static Config config = new Config("config/ActionBarInfo/config.json");
    public static Localization localization = new Localization("abi", "config/ActionBarInfo/lang");
    public static Minecraft MINECRAFT = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        config.load();
        AlinLibEvents.INIT.register(() -> {
            KeyMapping toggleKeyBind = KeyMappingHelper.register(new KeyMapping(
                    "abi.key.toggle",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                    "abi.name"
            ));
            KeyMapping toggleStopwatch = KeyMappingHelper.register(new KeyMapping(
                    "abi.key.stopwatch",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                    "abi.name"
            ));
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                assert client.player != null;
                while (toggleKeyBind.consumeClick()) {
                    config.setBoolean("ENABLE", !config.getBoolean("ENABLE", true));
                    config.save();
                }

                while (toggleStopwatch.consumeClick()) {
                    if(stateStopwatch == 0 && !config.getBoolean("ENABLE.STOPWATCH", true)) return;
                    stateStopwatch++;
                    if(stateStopwatch == 1) startStopwatch = System.currentTimeMillis();
                    if(stateStopwatch>2) stateStopwatch = 0;
                }
            });
        });
        LocalizationEvents.DEFAULT_PARSER_INIT.register((starScript -> starScript.ss.set("abi", new ValueMap()
                        .set("stopwatch", () -> Value.string(getStopwatch()))
                ))
        );
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            log("Client started!");
            start();
            HUDHandler hud = new HUDHandler();
            GuiRenderEvents.RENDER.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }

    public static String getMessage(){
        if(AlinLib.MINECRAFT.player == null || AlinLib.MINECRAFT.level == null) return "";
        StringBuilder builder = new StringBuilder(config.getString("INFO", ActionBarInfo.localization.getLocalization("info", false, true, false)));
        if(stateStopwatch > 0) builder.append("\\n").append(getStopwatch());
        return AlinLib.localization.getParsedText(Localization.fixFormatCodes(builder.toString()));
    }

    public static int stateStopwatch = 0;
    public static long startStopwatch = 0;
    public static long stopStopwatch = 0;

    public static String getStopwatch() {
        if (stateStopwatch == 0) startStopwatch = stopStopwatch = 0;
        else if (stateStopwatch == 1) stopStopwatch = System.currentTimeMillis();

        long milliseconds = stopStopwatch - startStopwatch;

        int ms = (int) milliseconds % 1000;
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, ms);
    }

    //
    public static void start() {
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isShowInfo() && (config.getNumber("TYPE_RENDER", 0).intValue() == 0 || config.getNumber("TYPE_RENDER", 0).intValue() > 5))
                    update();
            }
        }, 20, 20);
    }
    public static boolean isShowInfo(){
        if(config.getBoolean("MS4_MOMENT", false)) return isCompass();
        else return config.getBoolean("ENABLE", true);
    }

    public static boolean isCompass(){
            if(AlinLib.MINECRAFT.player == null) return false;
            return (AlinLib.MINECRAFT.player.getItemInHand(InteractionHand.MAIN_HAND).is(COMPASS) || AlinLib.MINECRAFT.player.getItemInHand(InteractionHand.OFF_HAND).is(COMPASS));
    }

    public static void update() {
        try {
            if (MINECRAFT.level == null || MINECRAFT.player == null) return;
            MINECRAFT.player.displayClientMessage(Localization.toText(getMessage().replace("\\n", " ")), true);
            if (lastException != null) lastException = null;
        } catch (Exception ex) {
            if (lastException == null || !lastException.equals(ex.getMessage())) {
                log(ex.getMessage(), Level.ERROR);
                lastException = ex.getMessage();
            }
        }
    }
}