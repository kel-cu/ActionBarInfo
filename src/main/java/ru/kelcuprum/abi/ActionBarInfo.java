package ru.kelcuprum.abi;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.abi.modules.ModulesManager;
import ru.kelcuprum.abi.modules.StopwatchModule;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.api.KeyMappingHelper;
import ru.kelcuprum.alinlib.api.events.alinlib.AlinLibEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientLifecycleEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientTickEvents;
import ru.kelcuprum.alinlib.api.events.client.GuiRenderEvents;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.gui.GuiUtils;

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
        ModulesManager.registerDefaultModules();
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

                while (toggleStopwatch.consumeClick() && config.getBoolean("module.actionbarinfo.stopwatch", true)) {
                    if(StopwatchModule.stateStopwatch == 0 && !config.getBoolean("ENABLE.STOPWATCH", true)) return;
                    StopwatchModule.stateStopwatch++;
                    if(StopwatchModule.stateStopwatch == 1) StopwatchModule.startStopwatch = System.currentTimeMillis();
                    if(StopwatchModule.stateStopwatch>2) StopwatchModule.stateStopwatch = 0;
                }
            });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            log("Client started!");
            start();
            HUDHandler hud = new HUDHandler();
            GuiRenderEvents.RENDER.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }

    @Deprecated
    public static String getMessage(){
        return "";
    }
    //
    public static void start() {
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isShowInfo() && (config.getNumber("TYPE_RENDER", 1).intValue() == 0 || config.getNumber("TYPE_RENDER", 1).intValue() > 5))
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
            MINECRAFT.player.displayClientMessage(ModulesManager.getText(), true);
            if (lastException != null) lastException = null;
        } catch (Exception ex) {
            if (lastException == null || !lastException.equals(ex.getMessage())) {
                log(ex.getMessage(), Level.ERROR);
                lastException = ex.getMessage();
            }
        }
    }

    public interface Icons {
        ResourceLocation MODULES = GuiUtils.getResourceLocation("actionbarinfo", "textures/gui/modules.png");
    }
}