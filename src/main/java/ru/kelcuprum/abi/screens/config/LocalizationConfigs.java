package ru.kelcuprum.abi.screens.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.impl.controller.StringControllerBuilderImpl;
import ru.kelcuprum.abi.localization.Localization;

public class LocalizationConfigs {
    public ConfigCategory getCategory() {
        ConfigCategory.Builder category = ConfigCategory.createBuilder()
                .name(Localization.getText("abi.localization"));
        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.info"))
                .binding(Localization.getLcnDefault("info"),
                        () -> Localization.getLocalization("info", false, false),
                        newVal -> Localization.setLocalization("info", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.address.hidden"))
                .binding(Localization.getLcnDefault("address.hidden"),
                        () -> Localization.getLocalization("address.hidden", false, false),
                        newVal -> Localization.setLocalization("address.hidden", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.singleplayer"))
                .binding(Localization.getLcnDefault("singleplayer"),
                        () -> Localization.getLocalization("singleplayer", false, false),
                        newVal -> Localization.setLocalization("singleplayer", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());
        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.date.time"))
                .binding(Localization.getLcnDefault("date.time"),
                        () -> Localization.getLocalization("date.time", false, false),
                        newVal -> Localization.setLocalization("date.time", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());


        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.north"))
                .binding(Localization.getLcnDefault("north"),
                        () -> Localization.getLocalization("north", false, false),
                        newVal -> Localization.setLocalization("north", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());

        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.south"))
                .binding(Localization.getLcnDefault("south"),
                        () -> Localization.getLocalization("south", false, false),
                        newVal -> Localization.setLocalization("south", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());

        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.west"))
                .binding(Localization.getLcnDefault("west"),
                        () -> Localization.getLocalization("west", false, false),
                        newVal -> Localization.setLocalization("west", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());

        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.east"))
                .binding(Localization.getLcnDefault("east"),
                        () -> Localization.getLocalization("east", false, false),
                        newVal -> Localization.setLocalization("east", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());

        category.option(Option.createBuilder(String.class)
                .name(Localization.getText("abi.localization.unknown"))
                .binding(Localization.getLcnDefault("unknown"),
                        () -> Localization.getLocalization("unknown", false, false),
                        newVal -> Localization.setLocalization("unknown", newVal))
                .controller(StringControllerBuilderImpl::new)
                .build());

        return category.build();
    }
}
