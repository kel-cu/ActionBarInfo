package ru.kelcuprum.abi;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.alinlib.api.KeyMappingHelper;
import ru.kelcuprum.alinlib.api.events.alinlib.AlinLibEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientLifecycleEvents;
import ru.kelcuprum.alinlib.api.events.client.ClientTickEvents;
import ru.kelcuprum.alinlib.api.events.client.GuiRenderEvents;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.config.Localization;

//#if FORGE
//$$ @net.minecraftforge.fml.common.Mod("actionbarinfo")
//#elseif NEOFORGE
//$$ @net.neoforged.fml.common.Mod("actionbarinfo")
//#endif
public class ActionBarInfo
        //#if FABRIC
        implements net.fabricmc.api.ClientModInitializer
        //#endif
{
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

    public void init() {
        config.load();
        AlinLibEvents.INIT.register(() -> {
            KeyMapping toggleKeyBind;
            toggleKeyBind = KeyMappingHelper.register(new KeyMapping(
                    "abi.key.toggle",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                    "abi.name"
            ));
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                assert client.player != null;
                while (toggleKeyBind.consumeClick()) {
                    config.setBoolean("ENABLE", !config.getBoolean("ENABLE", true));
                    config.save();
                }
            });
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            log("Client started!");
            start();
            HUDHandler hud = new HUDHandler();
            GuiRenderEvents.RENDER.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }

    //
    //#if FABRIC
    @Override
    public void onInitializeClient() {
        init();
    }
    //#elseif FORGE
    //$$  public ActionBarInfo(){
    //$$      init();
    //$$      if (net.minecraftforge.fml.loading.FMLLoader.getDist() == net.minecraftforge.api.distmarker.Dist.CLIENT) {
    //$$          registerScreen();
    //$$      }
    //$$  }
    //#elseif NEOFORGE
    //$$  public ActionBarInfo(){
    //$$      init();
    //$$      if (net.neoforged.fml.loading.FMLLoader.getDist() == net.neoforged.api.distmarker.Dist.CLIENT) {
    //$$          net.neoforged.fml.ModLoadingContext.get().registerExtensionPoint(
    //$$                  net.neoforged.neoforge.client.gui.IConfigScreenFactory.class,
    //$$                  () -> (minecraftClient, screen) -> new ru.kelcuprum.abi.screens.config.MainConfigsScreen().build(screen));
    //$$      }
    //$$  }
    //#endif

    //#if FORGE && MC < 12002
    //$$ public void registerScreen(){
    //$$          net.minecraftforge.fml.ModLoadingContext.get().registerExtensionPoint(
    //$$                  net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory.class,
    //$$                  () -> new net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory((java.util.function.Function<net.minecraft.client.gui.screens.Screen, net.minecraft.client.gui.screens.Screen>) new ru.kelcuprum.abi.screens.config.MainConfigsScreen()::build));
    //$$ }
    //#elseif FORGE && MC >= 12002
    //$$ public void registerScreen(){
    //$$          net.minecraftforge.fml.ModLoadingContext.get().registerExtensionPoint(
    //$$                  net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory.class,
    //$$                  () -> new net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory(new ru.kelcuprum.abi.screens.config.MainConfigsScreen()::build));
    //$$ }
    //#endif
    //
    public static void start() {
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (config.getBoolean("ENABLE", true) && (config.getNumber("TYPE_RENDER", 0).intValue() == 0 || config.getNumber("TYPE_RENDER", 0).intValue() > 5))
                    update();
            }
        }, 20, 20);
    }

    public static void update() {
        try {
            if (MINECRAFT.level == null || MINECRAFT.player == null) return;
            MINECRAFT.player.displayClientMessage(Localization.toText(localization.getLocalization("info").replace("\\n", " ")), true);
            if (lastException != null) lastException = null;
        } catch (Exception ex) {
            if (lastException == null || !lastException.equals(ex.getMessage())) {
                log(ex.getMessage(), Level.ERROR);
                lastException = ex.getMessage();
            }
        }
    }
}