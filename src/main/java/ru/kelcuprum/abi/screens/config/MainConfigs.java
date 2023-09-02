package ru.kelcuprum.abi.screens.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.impl.controller.BooleanControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.IntegerFieldControllerBuilderImpl;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.abi.config.UserConfig;

public class MainConfigs {
    public ConfigCategory getCategory() {
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
        category.option(Option.createBuilder(boolean.class)
//                .description(OptionDescription.createBuilder().text(Localization.getText("abi.config.render_in_debug_screen.tooltip")).build())
                .name(Localization.getText("abi.config.render_in_debug_screen"))
                .binding(false, () -> UserConfig.RENDER_IN_DEBUG_SCREEN,newVal -> UserConfig.RENDER_IN_DEBUG_SCREEN = newVal)
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        // type
        String[] types = {
                "Default",
                "ABI Render",
                "Bottom left",
                "Bottom right",
                "Top left",
                "Top right"
        };
        ResourceLocation typesImg= new ResourceLocation(ActionBarInfo.MOD_ID, "preview/types.png");
        category.option(Option.createBuilder(Integer.class)
                .name(Localization.getText("abi.config.type_render_action_bar"))
                .description(OptionDescription.createBuilder()
                        .image(typesImg, 854, 480)
                        .text(Localization.getText("abi.config.type_render_action_bar.tooltip")).build())
                .binding(1, () -> UserConfig.TYPE_RENDER_ACTION_BAR + 1, newVal -> UserConfig.TYPE_RENDER_ACTION_BAR = newVal-1)
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(1, types.length)
                        .step(1)
                        .valueFormatter(val -> Localization.toText(types[val-1])))
                .build());
        // intend
        ResourceLocation intendXImg= new ResourceLocation(ActionBarInfo.MOD_ID, "preview/intend_x.png");
        category.option(Option.createBuilder(int.class)
                .description(OptionDescription.createBuilder()
                        .image(intendXImg, 1278, 682)
                        .text(Localization.getText("abi.config.intend_x.tooltip")).build())
                .name(Localization.getText("abi.config.intend_x"))
                .binding(20, () -> UserConfig.INDENT_X,newVal -> UserConfig.INDENT_X = newVal)
                .controller(IntegerFieldControllerBuilderImpl::new)
                .build());

        ResourceLocation intendYImg= new ResourceLocation(ActionBarInfo.MOD_ID, "preview/intend_y.png");
        category.option(Option.createBuilder(int.class)
                .description(OptionDescription.createBuilder()
                        .image(intendYImg, 1278, 682)
                        .text(Localization.getText("abi.config.intend_y.tooltip")).build())
                .name(Localization.getText("abi.config.intend_y"))
                .binding(20, () -> UserConfig.INDENT_Y,newVal -> UserConfig.INDENT_Y = newVal)
                .controller(IntegerFieldControllerBuilderImpl::new)
                .build());
        return category.build();
    }
}
