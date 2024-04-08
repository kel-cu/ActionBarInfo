package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxLocalization;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.config.LocalizationScreen;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.alinlib.config.Localization;

public class LocalizationConfigsScreen {
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    private static final Component infoText = Localization.getText("abi.localization.info");
    private static final InterfaceUtils.DesignType designType = InterfaceUtils.DesignType.FLAT;
    public Screen build(Screen parent){
        return new ConfigScreenBuilder(parent, Component.translatable("abi.name"), designType)
                .addPanelWidget(new Button(10, 40, 110, 20, designType, MainConfigCategory, (OnPress) -> ActionBarInfo.MINECRAFT.setScreen(new MainConfigsScreen().build(parent))))
                .addPanelWidget(new Button(10, 65, 110, 20, designType, LocalizationConfigCategory, (OnPress) -> ActionBarInfo.MINECRAFT.setScreen(new LocalizationConfigsScreen().build(parent))))
                .addWidget(new TextBox(140, 5, LocalizationConfigCategory, true))
                .addWidget(new EditBoxLocalization(140, 30, designType, ActionBarInfo.localization, "info", infoText))
                .addWidget(new ButtonBuilder(Component.translatable("abi.localization.more"), (s) -> AlinLib.MINECRAFT.setScreen(LocalizationScreen.build(new LocalizationConfigsScreen().build(parent)))).build())
                .build();
    }
}
