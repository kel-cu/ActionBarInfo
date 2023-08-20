package ru.kelcuprum.abi.screens.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.impl.controller.BooleanControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.StringControllerBuilderImpl;
import net.minecraft.client.Minecraft;
import ru.kelcuprum.abi.config.Localization;
import ru.kelcuprum.abi.config.ServerConfig;
import ru.kelcuprum.abi.config.UserConfig;

public class ServerConfigs {
    public ConfigCategory getCategory() {
        Minecraft CLIENT = Minecraft.getInstance();
        ServerConfig.load();
        ConfigCategory.Builder category = ConfigCategory.createBuilder()
                .name(Localization.getText("abi.server"));
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.server.show_address.tooltip")).build())
                .name(Localization.getText("abi.server.show_address"))
                .binding(true, () -> ServerConfig.SHOW_ADDRESS,newVal -> ServerConfig.SHOW_ADDRESS = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.server.show_name_in_list.tooltip")).build())
                .name(Localization.getText("abi.server.show_name_in_list"))
                .binding(false, () -> ServerConfig.SHOW_NAME_IN_LIST,newVal -> ServerConfig.SHOW_NAME_IN_LIST = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.server.show_custom_name.tooltip")).build())
                .name(Localization.getText("abi.server.show_custom_name"))
                .binding(false, () -> ServerConfig.SHOW_CUSTOM_NAME,newVal -> ServerConfig.SHOW_CUSTOM_NAME = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(String.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.server.custom_name.tooltip")).build())
                .name(Localization.getText("abi.server.custom_name"))
                .binding("Custom name", () -> ServerConfig.CUSTOM_NAME,newVal -> ServerConfig.CUSTOM_NAME = newVal)
                .controller(StringControllerBuilderImpl::new)
                .build());
        return category.build();
    }
}
