package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.abi.modules.abstracts.Option;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.config.Localization;

public class DefaultModule extends AbstractModule {
    public DefaultModule() {
        super("default", "actionbarinfo", Component.translatable("abi.module.default"));
        options.add(new Option("INFO", ActionBarInfo.localization.getLocalization("info", false, false, false), Component.translatable("abi.localization.info"), Option.Type.STRING));
    }

    @Override
    public Component getMessage() {
        String message = ActionBarInfo.config.getString("INFO", ActionBarInfo.localization.getLocalization("info", false, false, false));
        message = Localization.fixFormatCodes(message);
        return Component.literal(AlinLib.localization.getParsedText(message));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
