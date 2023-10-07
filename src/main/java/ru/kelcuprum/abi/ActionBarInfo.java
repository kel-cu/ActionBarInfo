package ru.kelcuprum.abi;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.UserConfig;
import ru.kelcuprum.abi.localization.StarScript;
import ru.kelcuprum.abi.screens.ConfigScreen;

public class ActionBarInfo implements ClientModInitializer {
    public static final String MOD_ID = "actionbarinfo";
    public static DecimalFormat DF = new DecimalFormat("#.##");
    public static final Logger LOG = LogManager.getLogger("Action Bar Info");
    private static final Timer TIMER = new Timer();
    private static String lastException;
    public static void log(String message) { log(message, Level.INFO);}
    public static void log(String message, Level level) { LOG.log(level, "[" + LOG.getName() + "] " + message); }
    public static Boolean yetAnotherConfigLibV3 = FabricLoader.getInstance().getModContainer("yet_another_config_lib_v3").isPresent();
    @Override
    public void onInitializeClient() {
        StarScript.init();
        if(yetAnotherConfigLibV3){
            KeyMapping openConfigKeyBind;
            openConfigKeyBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                    "abi.key.openConfig",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                    "abi.name"
            ));
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                assert client.player != null;
                while (openConfigKeyBind.consumeClick()) {
                    final Screen current = client.screen;
                    Screen configScreen = ConfigScreen.buildScreen(current);
                    client.setScreen(configScreen);
                }
            });
        }
        KeyMapping toggleKeyBind;
        toggleKeyBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "abi.key.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "abi.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            while (toggleKeyBind.consumeClick()) {
                UserConfig.ENABLE_AB_INFORMATION = !UserConfig.ENABLE_AB_INFORMATION;
                UserConfig.save();
            }
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            UserConfig.load();
            log("Client started!");
            start();
            HUDHandler hud = new HUDHandler();
            HudRenderCallback.EVENT.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }
    public static void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(UserConfig.ENABLE_AB_INFORMATION && (UserConfig.TYPE_RENDER_ACTION_BAR == 0 || UserConfig.TYPE_RENDER_ACTION_BAR >5)) update();
            }
        }, 20, 20);
    }
    public static void update(){
        try{
            Minecraft client = Minecraft.getInstance();
            if(client.level == null || client.player == null) return;
            client.player.displayClientMessage(Localization.toText(
                    Localization.getLocalization("info", true, false)
            ), true);

            if(lastException != null) lastException = null;
        } catch (Exception ex){
            if(lastException == null || !lastException.equals(ex.getMessage())){
                log(ex.getMessage(), Level.ERROR);
                lastException = ex.getMessage();
            }
        }
    }
}