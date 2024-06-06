package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.selector.SelectorBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderIntegerBuilder;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;

public class MainConfigsScreen {
    public Screen build(Screen parent){
        return new ConfigScreenBuilder(parent, Component.translatable("abi.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.config"), (s) -> AlinLib.MINECRAFT.setScreen(new MainConfigsScreen().build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.localization"), (s) -> AlinLib.MINECRAFT.setScreen(new LocalizationConfigsScreen().build(parent))).build())
                .addWidget(new TextBox(Component.translatable("abi.config"), true))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("abi.config.enable_ab_information"), true).setConfig(ActionBarInfo.config, "ENABLE").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("alinlib.config.localization.extended_coordinates"), false).setConfig(AlinLib.bariumConfig, "LOCALIZATION.EXTENDED_COORDINATES").build())
                .addWidget(new SelectorBuilder(Component.translatable("abi.config.type_render_action_bar")).setList(new String[]{
                        "Default",
                        "ABI Render",
                        "Top left",
                        "Top right",
                        "Bottom left",
                        "Bottom right"
                }).setValue(0).setConfig(ActionBarInfo.config, "TYPE_RENDER").build())
                .addWidget(new SliderIntegerBuilder(Component.translatable("abi.config.intend_x")).setDefaultValue(20).setConfig(ActionBarInfo.config, "INDENT_X").setMin(5).setMax(100).build())
                .addWidget(new SliderIntegerBuilder(Component.translatable("abi.config.intend_y")).setDefaultValue(20).setConfig(ActionBarInfo.config, "INDENT_Y").setMin(5).setMax(100).build())
                .addWidget(new SliderIntegerBuilder(Component.translatable("abi.config.intend_abi_y")).setDefaultValue(85).setConfig(ActionBarInfo.config, "INDENT_ABI_Y").setMin(5).setMax(150).build())
                .build();
    }
}
