package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.abi.modules.abstracts.Option;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.utils.StealthManager;

public class DefaultModule extends AbstractModule {
    public static String DEFAULT_MESSAGE = "&6XYZ:&r {player.pos.x} {player.pos.y} {player.pos.z} &6{player.direction_symbol} {world.time_formatted}&r";
    public DefaultModule() {
        super("default", "actionbarinfo", Component.translatable("abi.module.default"));
        options.add(new Option("INFO", DEFAULT_MESSAGE, Component.translatable("abi.localization.info"), Option.Type.STRING));
        options.add(new Option("STEALTH", true, Component.translatable("abi.config.stealth"), Option.Type.BOOLEAN));
    }

    @Override
    public Component getMessage() {
        String message = ActionBarInfo.config.getString("INFO", DEFAULT_MESSAGE);
        message = Localization.fixFormatCodes(message);
        return Component.literal(AlinLib.localization.getParsedText(message));
    }

    @Override
    public boolean isEnabled() {
        return !ActionBarInfo.config.getBoolean("STEALTH", true) || !StealthManager.isStealthActive();
    }
}
