package ru.kelcuprum.abi.screens.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.ModulesManager;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.abi.modules.abstracts.Option;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.text.HorizontalRuleBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.text.TextBuilder;
import ru.kelcuprum.alinlib.gui.components.text.CategoryBox;
import ru.kelcuprum.alinlib.gui.config.LocalizationScreen;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;

import java.util.ArrayList;

import static ru.kelcuprum.abi.ActionBarInfo.Icons.MODULES;
import static ru.kelcuprum.alinlib.gui.Icons.LIST;
import static ru.kelcuprum.alinlib.gui.Icons.OPTIONS;

public class ModulesScreen {
    public static Screen build(Screen parent){
        ConfigScreenBuilder builder =  new ConfigScreenBuilder(parent, Component.translatable("abi.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.configs")).setCentered(false).setOnPress((s) -> AlinLib.MINECRAFT.setScreen(MainConfigsScreen.build(parent))).setIcon(OPTIONS))
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.modules")).setCentered(false).setOnPress((s) -> AlinLib.MINECRAFT.setScreen(build(parent))).setIcon(MODULES))
                .addPanelWidget(new ButtonBuilder(Component.translatable("abi.localization.more"), (s) -> AlinLib.MINECRAFT.setScreen(LocalizationScreen.build(parent))).setCentered(false).setIcon(LIST).build())

                .addWidget(new TextBuilder(Component.translatable("abi.modules")));

        for(String mod_id : ModulesManager.modsModules.keySet()){
            ArrayList<String> modulesID = ModulesManager.modsModules.getOrDefault(mod_id, new ArrayList<>());
            ArrayList<AbstractModule> modules = new ArrayList<>();
            for(String moduleID : modulesID) modules.add(ModulesManager.modules.get(moduleID));
            String name = FabricLoader.getInstance().isModLoaded(mod_id) ? FabricLoader.getInstance().getModContainer(mod_id).get().getMetadata().getName() : mod_id;
            builder.addWidget(new HorizontalRuleBuilder(Component.translatable("abi.modules.mod", name, modulesID.size())));
            for(AbstractModule module : modules){
                if(!module.options.isEmpty()){
                    CategoryBox categoryBox = new CategoryBox(module.name);
                    categoryBox.addValue(new ButtonBooleanBuilder(Component.translatable("abi.modules.enable"), module.enabledByDefault).setConfig(ActionBarInfo.config, String.format("module.%s.%s", module.mod_id, module.id)));
                    for(Option option : module.options){
                        categoryBox.addValue(option.getBuilder());
                    }
                    categoryBox.changeState(false);
                    builder.addWidget(categoryBox);
                } else {
                    builder.addWidget(new ButtonBooleanBuilder(module.name, module.enabledByDefault).setConfig(ActionBarInfo.config, String.format("module.%s.%s", module.mod_id, module.id)));
                }
            }
        }

        return builder.build();
    }
}
