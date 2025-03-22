package ru.kelcuprum.abi.modules;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.modules.abstracts.AbstractModule;
import ru.kelcuprum.alinlib.AlinLib;

public class DirectionModule extends AbstractModule {
    public DirectionModule() {
        super("direction", "actionbarinfo", Component.translatable("abi.module.direction"), false);
    }

    private static final String compass_text_simple = "S ] ------- [ SW ] ------- [ W ] ------- [ NW ] ------- [ N ] ------- [ NE ] ------- [ E ] ------- [ E ] ------- [ ";
    private static final String compass_text_triple = compass_text_simple + compass_text_simple + compass_text_simple;
    private static final int oneSideLength = 16;

    @Override
    public Component getMessage() {
        if(AlinLib.MINECRAFT.player == null) return Component.empty();
        float modYaw = (AlinLib.MINECRAFT.player.getYHeadRot() % 360.0f + 360.0f) % 360.0f;
        String renderText = displayedText(modYaw);
        return Component.literal(AlinLib.localization.getParsedText(renderText));
    }

    private static String displayedText(float yaw) {
        int textAnchor = Math.round((yaw / 360.0f) * compass_text_simple.length()) + compass_text_simple.length();
        return compass_text_triple.substring(textAnchor - oneSideLength, textAnchor + oneSideLength);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
