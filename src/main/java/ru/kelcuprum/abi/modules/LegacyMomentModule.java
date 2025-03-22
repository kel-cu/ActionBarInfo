package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;

@Deprecated
public class LegacyMomentModule extends AbstractModule {
    public LegacyMomentModule() {
        super("legacy", "actionbarinfo", Component.translatable("abi.module.legacy"));
    }

    @Override
    public Component getMessage() {
        String message = ActionBarInfo.getMessage();
        return Component.literal(message);
    }

    @Override
    public boolean isEnabled() {
        return !ActionBarInfo.getMessage().isBlank();
    }
}
