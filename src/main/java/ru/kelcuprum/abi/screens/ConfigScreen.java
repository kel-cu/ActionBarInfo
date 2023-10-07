package ru.kelcuprum.abi.screens;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.ServerConfig;
import ru.kelcuprum.abi.config.UserConfig;
import ru.kelcuprum.abi.screens.config.LocalizationConfigs;
import ru.kelcuprum.abi.screens.config.MainConfigs;
import ru.kelcuprum.abi.screens.config.ServerConfigs;

public class ConfigScreen {
    public static Screen buildScreen (Screen currentScreen) {
        Minecraft CLIENT = Minecraft.getInstance();
        YetAnotherConfigLib.Builder screen = YetAnotherConfigLib.createBuilder()
                .title(Localization.getText("abi.name"))
                .save(ConfigScreen::save);
        screen.category(new MainConfigs().getCategory());
        if(!CLIENT.isSingleplayer() && CLIENT.getCurrentServer() != null) {
            screen.category(new ServerConfigs().getCategory());
        }
        screen.category(new LocalizationConfigs().getCategory());
        return screen.build().generateScreen(currentScreen);
    }
    private static void save(){
        Minecraft CLIENT = Minecraft.getInstance();
        ActionBarInfo.log("Saving settings...");
        UserConfig.save();
        if(!CLIENT.isSingleplayer() && CLIENT.getCurrentServer() != null){
            ActionBarInfo.log("Save server configs...");
            ServerConfig.save();
        }
    }
}
