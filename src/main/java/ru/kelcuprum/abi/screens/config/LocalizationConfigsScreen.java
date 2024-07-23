package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.editbox.EditBoxBuilder;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.config.LocalizationScreen;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;

import static ru.kelcuprum.alinlib.gui.Icons.*;

public class LocalizationConfigsScreen {
    public Screen build(Screen parent){
        return new ConfigScreenBuilder(parent, Component.translatable("abi.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.config"), (s) -> AlinLib.MINECRAFT.setScreen(new MainConfigsScreen().build(parent))).setIcon(OPTIONS).setCentered(false).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.localization"), (s) -> AlinLib.MINECRAFT.setScreen(new LocalizationConfigsScreen().build(parent))).setIcon(LIST).setCentered(false).build())
                .addWidget(new TextBox(Component.translatable("abi.localization"), true))
                .addWidget(new EditBoxBuilder(Component.translatable("abi.localization.info")).setLocalization(ActionBarInfo.localization, "info").build())
                .addWidget(new ButtonBuilder(Component.translatable("abi.localization.more"), (s) -> AlinLib.MINECRAFT.setScreen(LocalizationScreen.build(new LocalizationConfigsScreen().build(parent)))).setIcon(LIST).build())
                .build();
    }
}
