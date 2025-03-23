package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.abi.modules.abstracts.Option;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.utils.StealthManager;

public class DefaultModule extends AbstractModule {
    public DefaultModule() {
        super("default", "actionbarinfo", Component.translatable("abi.module.default"));
        options.add(new Option("INFO", ActionBarInfo.localization.getLocalization("info", false, false, false), Component.translatable("abi.localization.info"), Option.Type.STRING));
        options.add(new Option("STEALTH", true, Component.translatable("abi.config.stealth"), Option.Type.BOOLEAN));
    }

    @Override
    public Component getMessage() {
        String message = ActionBarInfo.config.getString("INFO", ActionBarInfo.localization.getLocalization("info", false, false, false));
        message = Localization.fixFormatCodes(message);
        return Component.literal(AlinLib.localization.getParsedText(message));
    }

    @Override
    public boolean isEnabled() {
        return !ActionBarInfo.config.getBoolean("STEALTH", true) || !StealthManager.isStealthActive();
    }
}
