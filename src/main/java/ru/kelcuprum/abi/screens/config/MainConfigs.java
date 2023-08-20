package ru.kelcuprum.abi.screens.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.impl.controller.BooleanControllerBuilderImpl;
import net.minecraft.client.Minecraft;
import ru.kelcuprum.abi.config.Localization;
import ru.kelcuprum.abi.config.UserConfig;

public class MainConfigs {
    public ConfigCategory getCategory() {
        Minecraft CLIENT = Minecraft.getInstance();
        UserConfig.load();
        ConfigCategory.Builder category = ConfigCategory.createBuilder()
                .name(Localization.getText("abi.config"));
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.config.enable_ab_information.tooltip")).build())
                .name(Localization.getText("abi.config.enable_ab_information"))
                .binding(true, () -> UserConfig.ENABLE_AB_INFORMATION,newVal -> UserConfig.ENABLE_AB_INFORMATION = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.config.use_extended_coordinates.tooltip")).build())
                .name(Localization.getText("abi.config.use_extended_coordinates"))
                .binding(false, () -> UserConfig.USE_EXTENDED_COORDINATES,newVal -> UserConfig.USE_EXTENDED_COORDINATES = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("abi.config.view_item_off_hand.tooltip")).build())
                .name(Localization.getText("abi.config.view_item_off_hand"))
                .binding(false, () -> UserConfig.VIEW_ITEM_OFF_HAND,newVal -> UserConfig.VIEW_ITEM_OFF_HAND = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        return category.build();
    }
}
